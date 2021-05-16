package Model.Entitys.Monsters;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Inventory;
import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe décrivant le marchand
 * @author Gillian, Quentin
 */
public class Marchand extends AbstractMonster {
    ArrayList<AbstractItem> itemArrayList = new ArrayList<>();
    private static STATE state = STATE.NOTVISITED;
    private final String couleurText = Affichage.GREEN;
    private final String couleurTouches = Affichage.BRIGTH_PURPLE;
    private final String couleurItems = Affichage.YELLOW;
    private final String couleurPrix = Affichage.BRIGTH_YELLOW;

    /**
     * Différents états du marchands
     * @author Gillian, Quentin
     */
    public enum STATE {
        NOTVISITED, NORMAL, DEAD, AGGRESSIVE, ACHAT, CONFIRMATION, VENTE
    }

    /**
     * Classe du marchand
     * @param m             Etage où mettre le marchand
     * @param pos           Position du marchand
     * @param nom           Nom du marchand
     * @param pv            Pv de base du marchand
     * @param force         Force de base du marchand
     * @param vision_radius Portée de vision du marchand
     * @param agro          Agro
     * @param update_rate   Taux de raffraichissement
     * @param pathCross     Type de déplacement
     * @param lvl           niveau du marchand
     * @param nbrWeaponsMax Nombre d'arme max que peut vendre le marchand
     * @param nbrPotionsMax Nombre de potion max que peut vendre le marchand
     */
    public Marchand(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate, Tools.PathType pathCross, int lvl, int nbrWeaponsMax, int nbrPotionsMax) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
        generateStock(nbrWeaponsMax,nbrPotionsMax);
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        if(state == STATE.AGGRESSIVE){
            return super.updatePV(pv,limited);
        }
        else{
            changeDialogueState();
            dialogue();
            return true;
        }
    }

    @Override
    public void updateMonster() {
        if(state == STATE.AGGRESSIVE){
            super.updateMonster();
        }
    }

    @Override
    public void death() {
        super.death();
        state = STATE.DEAD;
        for(AbstractItem item : itemArrayList){
            getInventory().dropItem(this,item);
        }
        TourManager.addMessage(Affichage.RED + Affichage.BOLD + "Tuer le seul marchand du labyrinthe n'etait peut etre pas une bonne idée.");
    }

    /**
     * Dialogue du marchand de base.
     * @author Quentin
     */
    private void dialogue(){
        StringBuilder sb = new StringBuilder();
        if(state==STATE.NOTVISITED){
            sb.append(Affichage.BRIGTH_YELLOW).append(Affichage.BOLD).append("\nBonjour ").append(Objects.requireNonNull(Start.getPlayer()).getNom());
            sb.append("\nBienvenue chez le plus grand, le pLus beau, le plus charismatique marchand de tout le labyrinthe.");
            sb.append("\nCela fait maintenant près de 10 ans que je suis bloqué dans ce labyrinthe.");
            sb.append("Je suis donc l'homme le plus riiiiche que tu puisses trouver ici");
            sb.append("\nTu te doutes bien que toute cette fortune n'est pas sortie de nulle part !");
            sb.append("\nJe vends, j'achète, je vo... enfin bref, je mène mon buisness quoi !\n\n");
            state = STATE.NORMAL;
        }
        sb.append(couleurText).append("Tu es ici pour acheter un de mes merveilleux objets ou pour me vendre un des tiens ?\n\n");
        sb.append("Acheter - ").append(couleurTouches).append("1\n");
        sb.append(couleurText).append("Vendre - ").append(couleurTouches).append("2\n");
        sb.append(couleurText).append("Voler - ").append(couleurTouches).append("3\n");
        sb.append(couleurText).append("Quitter - ").append(couleurTouches).append("4\n");
        System.out.print(sb);
        try {
            processInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Defini le comportement selon la touche et l'etat du marchand.
     * @return Si la confiramtion est vrai ou fausse
     * @throws IOException Exception
     * @author Quentin
     */
    private boolean processInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String string = reader.readLine();
        boolean result = false;
        switch (state){
            case NORMAL -> {
                switch (string){
                    case "1" -> startAchat();
                    case "2" -> startVente();
                    case "3" -> startAttaque();
                    default -> {
                        System.out.println("A la prochaine");
                        changeDialogueState();
                    }
                }
            }
            case ACHAT -> {
                switch (string){
                    case "1","2","3","4","5","6","7","8","9" -> startAchatSelection(Integer.parseInt(string));
                    case "q", "Q" -> {
                        state = STATE.NORMAL;
                        dialogue();
                    }
                    default -> {
                        System.out.println(Affichage.RED+"MAUVAISE TOUCHE");
                        processInput();
                    }
                }
            }
            case VENTE -> {
                Inventory inventory = Objects.requireNonNull(Start.getPlayer()).getInventory();
                switch (string){
                    case "i", "I" -> {
                        inventory.switchWeapons();
                        System.out.println(couleurText+ "Vos armes:");
                        System.out.print(getListeItem(new ArrayList<>(inventory.getWeapons())));
                        processInput();
                    }
                    case "o", "O" -> {
                        inventory.switchPotions();
                        System.out.println(couleurText + "Vos potions:");
                        System.out.print(getListeItem(new ArrayList<>(inventory.getPotions())));
                        processInput();
                    }
                    case "l", "L" -> startVenteSelection(true);
                    case "m", "M" -> startVenteSelection(false);
                    case "q", "Q" -> {
                        state = STATE.NORMAL;
                        dialogue();
                    }
                    default -> {
                        System.out.println(Affichage.RED+"MAUVAISE TOUCHE");
                        processInput();
                    }
                }
            }
            case CONFIRMATION -> {
                switch (string){
                    case "y", "Y" -> result = true;
                    default -> {}
                }
            }
        }
        return result;
    }

    /**
     * Demarre la phase d'achat d'objet du marchand.
     * @author Quentin
     */
    private void startAchat(){
        state = STATE.ACHAT;
        StringBuilder sb = new StringBuilder();
        sb.append(couleurText);
        if(itemArrayList.size()==0){
            sb.append("Je n'ai plus rien en stock.\n");
        }
        else{
            sb.append("J'ai de amrmes et des potions a vendre, que voulez vous ?");
            if(getInventory().getWeapons().size()>0){
                sb.append(couleurText).append("Voici d'abord mes armes\n");
            }
            for (int i = 0; i < itemArrayList.size(); i++) {
                if(i==getInventory().getWeapons().size() && getInventory().getPotions().size()!=0){
                    sb.append(couleurText).append("\nMais aussi mes belles et succulentes potions ... \n\n");
                }
                AbstractItem item = itemArrayList.get(i);
                sb.append(couleurItems).append(item.getNom()).append(": ").append(item);
                sb.append(couleurText).append("  pour ");
                sb.append(couleurPrix).append(item.getPrix()).append("$");
                sb.append(couleurText).append(" ---> ");
                sb.append(couleurTouches).append(i).append("\n");
            }
        }
        if(itemArrayList.size()!=0){
            sb.append(couleurText).append("Il te suffit d'appuyer sur la touche correspondante pour m'acheter un de mes merveilleux objet.\n");
            sb.append("Ou sur ").append(couleurTouches).append("Q").append(couleurText).append(" pour quitter.");
        }
        System.out.println(sb);
        try {
            processInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Procedure d'achat de l'item a l'index passé en parametre.
     * @param index Index de l'item dans l'ArrayList du marchand
     * @author Quentin
     */
    private void startAchatSelection(int index){
        state = STATE.CONFIRMATION;
        AbstractItem abstractItem = itemArrayList.get(index);
        System.out.println(getConfirmationItem(abstractItem,false));
        try {
            if(processInput()){
                if(Objects.requireNonNull(Start.getPlayer()).getMoney()>=abstractItem.getPrix()){
                    Inventory inventory = Start.getPlayer().getInventory();
                    if(index>=getInventory().getWeapons().size()){
                        if(inventory.isPotionsFull()){
                            if(inventory.dropItem(Start.getPlayer(),abstractItem)){
                                getInventory().getPotions().remove((AbstractPotion)abstractItem);
                                itemArrayList.remove(abstractItem);
                            }
                            else{
                                System.out.println(couleurText + "L'item ne peut pas etre acheté l'endroit est trop encombré.");
                            }
                        }
                        else{
                            inventory.addWeapon((AbstractWeapon) abstractItem);
                        }
                    }
                    else{
                        if(inventory.isWeaponsFull()){
                            if(inventory.dropItem(Start.getPlayer(),abstractItem)){
                                getInventory().getWeapons().remove((AbstractWeapon) abstractItem);
                                itemArrayList.remove(abstractItem);
                            }
                            else{
                                System.out.println(couleurText + "L'item ne peut pas etre acheté l'endroit est trop encombré.");
                            }
                        }
                        else{
                            inventory.addPotion((AbstractPotion) abstractItem);
                        }
                    }
                    Start.getPlayer().removeMoney(abstractItem.getPrix());
                    System.out.println(couleurText +  "Félicitation pour cet achat!!!");
                }
                else{
                    System.out.println(couleurText +  "Vous n'avez pas assez d'argent pour cet objet.");
                }
            }
            state = STATE.NORMAL;
            dialogue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demare la phase de vente du marchand.
     * @author Quentin
     */
    private void startVente(){
        state = STATE.VENTE;
        StringBuilder sb = new StringBuilder();
        sb.append(couleurText).append("Qu'avez vous a me vendre ?\n");
        sb.append("Vous pouver changer vous armes et potions comme bon vous semble avec ");
        sb.append(couleurTouches).append("I").append(couleurText).append(" et ").append(couleurTouches).append("0").append("\n");
        sb.append(couleurText).append("Appuyez sur ").append(couleurTouches).append("L");
        sb.append(couleurText).append(" pour me vendre une arme et ").append(couleurTouches).append("M");
        sb.append(couleurText).append(" pour me vendre une potion ou ").append(couleurTouches).append("Q").append(couleurText).append(" pour quitter\n");
        sb.append(couleurText).append("Vos armes:\n");
        sb.append(getListeItem(new ArrayList<>(Objects.requireNonNull(Start.getPlayer()).getInventory().getWeapons())));
        sb.append(couleurText).append("Vos potions:\n");
        sb.append(getListeItem(new ArrayList<>(Start.getPlayer().getInventory().getPotions())));
        System.out.println(sb);
        try{
            processInput();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Confirmation de la vente de l'objet courant du joueur, son arme si venteArme est vrai et sa potion si venteArme est faux.
     * @param venteArme Defini si je joueur vent son arme ou sa potion courrante
     * @author Quentin
     */
    private void startVenteSelection(boolean venteArme){
        state = STATE.CONFIRMATION;
        Player player = Start.getPlayer();
        AbstractItem abstractItem = venteArme ? Objects.requireNonNull(player).getInventory().getCurrentWeapon() : Objects.requireNonNull(player).getInventory().getCurrentPotion();
        StringBuilder sb = new StringBuilder();
        if(abstractItem==null){
            sb.append(couleurText).append("Malheureusement vous n'avez rien a me vendre.\n");
        }
        else{
            sb.append(getConfirmationItem(abstractItem,true));
        }
        System.out.print(sb);
        try {
            if(abstractItem != null){
                if(processInput()){
                    if(venteArme){
                        player.getInventory().getWeapons().remove(abstractItem);
                    }
                    else{
                        player.getInventory().getPotions().remove(abstractItem);
                    }
                    player.addMoney(abstractItem.getPrix());
                    System.out.println(couleurText + "Merci pour cette vente.");
                }
            }
            state = STATE.NORMAL;
            dialogue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genere le stock du marchand.
     * @param nbrWeaponsMax Le nombre d'arme du marchand
     * @param nbrPotionsMax le nombre de potions du marchand
     * @author Quentin
     */
    private void generateStock(int nbrWeaponsMax, int nbrPotionsMax){
        for (int i = 0; i < Procedure.getRandomInt(nbrWeaponsMax,0); i++) {
            getInventory().getWeapons().add(switch (Procedure.getRandomInt(3, 1)) {
                case 1 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.SWORD);
                case 2 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.WAND);
                default -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.BOW);
            });
        }
        for (int i = 0; i < Procedure.getRandomInt(nbrPotionsMax,0); i++) {
            getInventory().getPotions().add(switch (Procedure.getRandomInt(20, 1)) {
                case 1, 2, 3, 4, 5, 6, 7 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.HEAL_POTION);
                case 8, 9, 10 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.ENDURENCE_POTION);
                case 11, 12, 13 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.INVUL_POTION);
                default -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.STRENGTH_POTION);
            });
        }
        itemArrayList.addAll(getInventory().getWeapons());
        itemArrayList.addAll(getInventory().getPotions());
    }

    /**
     * Commmence la phase d'attaque du marchand, il va devenir un vrai mob et attaquer le joueur.
     * @author Quentin
     */
    private void startAttaque(){
        state = STATE.AGGRESSIVE;
        TourManager.addMessage(Affichage.YELLOW +
                "Vous auriez pu choisir la fortune... " +
                Objects.requireNonNull(Start.getPlayer()).getNom() +
                "\n" + Affichage.RED +
                "Mais vous avez choisi la " +
                Affichage.BRIGTH_RED + "MORT" + Affichage.RED + " !!!!");
        changeDialogueState();
    }

    /**
     * Renvoit un String contenant la liste des items passé en parametre avec leurs prix et leurs nom.
     * @param items Liste des items
     * @return String
     * @author Quentin
     */
    private String getListeItem(ArrayList<AbstractItem> items){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            AbstractItem item = items.get(i);
            sb.append(Affichage.YELLOW).append(item.getNom()).append(" ");
            if(i==0){
                sb.append(Affichage.RED).append("[ ").append(Affichage.RESET).append(item).append(Affichage.BOLD).append(Affichage.RED).append(" ]").append(Affichage.RESET).append(Affichage.BOLD);
            }
            else{
                sb.append(item);
            }
            sb.append(Affichage.YELLOW).append(" : ").append(Affichage.BRIGTH_YELLOW).append(item.getPrix()).append("$").append("\n");
        }
        return sb.toString();
    }

    /**
     * Renvoit le string de demande de confirmation d'achat/de vente.
     * @param item Item acheté/vendu
     * @param isVente True si vente False si achat
     * @return String
     * @author Quentin
     */
    private String getConfirmationItem(AbstractItem item,boolean isVente){
        return couleurText + "Etes vous sur de vouloir " + (isVente ? "me vendre: " : "acheter: ") +
                couleurItems + item.getNom() + " " + Affichage.RESET + Affichage.BOLD + item +
                couleurText + " pour " + couleurPrix + item.getPrix() + "$" + couleurText + " ?\n" +
                couleurTouches + "Y" + couleurText + " -> OUI\n" +
                couleurTouches + "N" + couleurText + " -> NON\n";
    }

    /**
     * Permet de passer du mode dialogue avec le marchand a celui de joueur dans le jeu.
     * @author Quentin
     */
    private void changeDialogueState() {
        switch (state){
            case AGGRESSIVE, NOTVISITED -> {}
            default -> state = STATE.NORMAL;
        }
        TourManager.Pause();
        TourManager.InDialogue();
    }

    /**
     * Renvoit l'etat du marchand.
     * @return Son etat
     * @author Quentin
     */
    public static STATE getState() {
        return state;
    }

    @Override
    public String toString() {
        if (System.getProperty("os.name").equals("Linux")) {
            return "\uD83D\uDC73";
        } else {
            return Affichage.YELLOW + Affichage.BOLD + Affichage.ITALIC + "£";
        }
    }
}