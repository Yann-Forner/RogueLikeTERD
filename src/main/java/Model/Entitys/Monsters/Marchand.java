package Model.Entitys.Monsters;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.Wand;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Map.Etage;
import Model.Utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


//TODO Faut changer la salle en fonction du statut du marchand
//TODO Système de vente
//TODO Système d'achat
//TODO déplacer money dans inventory

/**
 * Classe décrivant le marchand (considéré donc comme un monstre
 *
 * @author Gillian, Quentin
 */
public class Marchand extends AbstractMonster {
    ArrayList<AbstractItem> itemArrayList = new ArrayList<>();

    /**
     * Différents états du marchands
     *
     * @author Gillian
     */
    public enum STATE {
        NOTVISITED, VISITED, BUY, BUYSELECTION, BUYCONFIRMATION, SELL, AGGRESSIVE, SELLWEAPONS, SELLPOTIONS, SELLEMPTY, SELLCONFIRMATION
    }

    private STATE state;


    /**
     * Constructeur du marchand
     *
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
     * @param state         Etat du marchand
     * @author Gillian
     */
    public Marchand(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate, Tools.PathType pathCross, int lvl, STATE state) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
        this.state = state;
        generateItems(3,5);
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        switch (state) {
            case NOTVISITED, VISITED -> dialogue(true);
            case BUY -> System.out.println("buy"); //TODO
            case AGGRESSIVE -> System.out.println("agrressive"); //TODO
            case SELL -> System.out.println("sell");
        }
        return true;
    }

    /**
     * Dialogue de "bienvenue" lancé lorsque l'on a jamais vu le marchand.
     *
     * @author Gillian, Quentin
     */
    private String dialogueInit() {
        state = STATE.VISITED;
        return Affichage.BRIGTH_YELLOW + Affichage.BOLD +
                "\nBonjour " +
                Start.getPlayer().getNom() +
                "\nBienvenue chez le plus grand, le pLus beau, le plus charismatique marchand de tout le labyrinthe." +
                "\nCela fait maintenant près de 10 ans que je suis bloqué dans ce labyrinthe." +
                "Je suis donc l'homme le plus riiiiche que tu puisses trouver ici" +
                "\nTu te doutes bien que toute cette fortune n'est pas sortie de nulle part !" +
                "\nJe vends, j'achète, je vo... enfin bref, je mène mon buisness quoi !\n\n";
    }

    /**
     * Dialogue de déclaration des options de vente / d'achat / d'attaque.
     * @author Gillian, Quentin
     */
    public void dialogue(boolean changeState) {
        if(changeState){
            changeDialogueState();
        }
        StringBuilder sb = new StringBuilder();
        switch (state) {
            case VISITED -> sb.append("Comme on se retrouve !\n\n");
            case NOTVISITED -> sb.append(dialogueInit());
        }
        sb.append("Tu es ici pour acheter un de mes merveilleux objets ou pour me vendre un des tiens ?\n\n");
        sb.append(Affichage.GREEN);
        sb.append("1 - Acheter\n");
        sb.append(Affichage.YELLOW);
        sb.append("2 - Vendre\n");
        sb.append(Affichage.RED);
        sb.append("3 - Attaquer\n");
        sb.append(Affichage.BLUE);
        sb.append("4 - Quitter\n");
        System.out.println(sb);
        try {
            processInput();
        }
        catch (IOException ignored) { }
    }


    /**
     * Analyse de l'entrée utilisateur et redirection vers les différentes procédures.
     * @throws IOException Si le reader ne fonctionne pas
     * @author Gillian, Quentin
     */
    private void processInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String string = reader.readLine();
        switch (state) {
            case BUYSELECTION -> {
                switch (string) {
                    case "0","1","2","3","4","5","6","7","8","9" -> buying(Integer.parseInt(string));
                    default -> processInput();
                }
            }
            case BUYCONFIRMATION -> {
                switch (string) {
                    case "y", "Y" -> dialogueBuy();
                    case "n", "N" -> dialogue(false);
                    default -> processInput();
                }
            }
            case SELLPOTIONS -> {
                switch (string){
                    case "p", "P" -> procedureSell(2);
                    case "v", "V" -> selling();
                    case "4" -> {}
                    default -> processInput();
                }
            }
            case SELLWEAPONS -> {
                switch (string){
                    case "w", "W" -> procedureSell(1);
                    case "v", "V" -> selling();
                    case "4" -> {}
                    default -> processInput();
                }
            }
            case SELL -> {
                switch (string) {
                    case "w", "W" -> procedureSell(1);
                    case "p", "P" -> procedureSell(2);
                    case "v", "V" -> selling();
                    case "4" -> {}
                    default -> processInput();
                }
            }
            case SELLCONFIRMATION -> {
                switch (string) {
                    case "y", "Y" -> dialogueSell();
                    case "n", "N" -> dialogue(false);
                    default -> processInput();
                }

            }
            default -> {
                switch (string) {
                    case "1" -> dialogueInitBuy();
                    case "2" -> dialogueSell();
                    case "3" -> typeAggressive();
                    case "4" -> {}
                    //case "y", "Y" -> procedureYes();
                    //case "n", "N" -> procedureNo();
                    case "w", "W" -> procedureSell(1);
                    case "p", "P" -> procedureSell(2);
                    case "v", "V" -> selling();
                    default -> processInput();
                }

            }

        }
        changeDialogueState();
    }





    /**
     * Procédure d'attaque lorsque le marchand est aggressif.
     *
     * @author Gillian, Quentin
     */
    public void updateMonster() {
        if (state == STATE.AGGRESSIVE) {
            super.updateMonster();
        }
    }

    //// ------------------------------------------Achat-------------------------------------------------------------

    //TODO créer une liste d'item du marchand
    //TODO Pouvoir parcourir la liste d'item du marchand
    //TODO deux options : soit on agglomère les listes pour en former une, soit on différencie les deux à la vente
    //TODO drop au sol

    /**
     * Dialogue initial de la vente
     *
     * @author Gillian
     */

    public void dialogueInitBuy() {
        state = STATE.BUY;
        String sb = Affichage.GREEN +
                "Tu veux donc acquérir un de mes merveilleux objets ? EHEH ! ça me va !\n" +
                "Voici tous mes beaux objets !\n";
        System.out.println(sb);
        dialogueBuy();
    }

    /**
     * Dialogue permettant de montrer les différents items du marchand
     * @author Gillian
     */
    public void dialogueBuy(){
        StringBuilder sb = new StringBuilder();
        if(getInventory().getWeapons().size()!=0){
            sb.append(Affichage.GREEN).append("Voici tout d'abord mes armes... \n\n");
        }
        for (int i = 0; i < itemArrayList.size(); i++) {
            if(i==getInventory().getWeapons().size() && getInventory().getPotions().size()!=0){
                sb.append(Affichage.GREEN).append("\nMais aussi mes belles et succulentes potions ... \n\n");
            }
            AbstractItem item = itemArrayList.get(i);
            sb.append(Affichage.YELLOW);
            sb.append(item.getNom());
            sb.append(": ");
            sb.append(item);
            sb.append("  pour ");
            sb.append(item.getPrix());
            sb.append("$");
            sb.append("   Touche ---> ");
            sb.append(i);
            sb.append("\n");
        }
        sb.append(Affichage.GREEN).append(itemArrayList.size()==0 ?
                "Je n'ai plus rien en stock.\n" :
                "\nIl te suffit d'appuyer sur la touche correspondante pour m'acheter un de mes merveilleux objet.");
        System.out.println(sb);
        if(itemArrayList.size()==0){
            state = STATE.VISITED;
            dialogue(false);
        }
        else{
            setState(STATE.BUYSELECTION);
            try {
                processInput();
            } catch (Exception e) { }
        }
    }

    /**
     * Procédure de la vente de l'objet sélectionné
     * @param index place de l'objet vendu dans la liste
     *
     * @author Gillian
     */

    public void buying (int index) {
        //TODO supp doublons
        if (index>itemArrayList.size()-1){
            StringBuilder sb = new StringBuilder();
            sb.append(Affichage.RED);
            sb.append("Oups, je crois que tu t'es trompé de touche ");
            System.out.println(sb);
            dialogueBuy();
        }
        else {
            if (checkMoney(itemArrayList.get(index))) {
                AbstractItem item = itemArrayList.remove(index);
                Start.getPlayer().removeMoney(item.getPrix());
                getInventory().dropItem(this, item);

                setState(STATE.BUYCONFIRMATION);

                StringBuilder sb = new StringBuilder();
                sb.append("Félicitations, tu viens de m'acheter ");
                sb.append(Affichage.YELLOW);
                sb.append(item.getNom());
                sb.append(Affichage.GREEN);
                sb.append(" pour la belle somme de ");
                sb.append(Affichage.YELLOW);
                sb.append(item.getPrix());
                sb.append("\n\n");
                sb.append(Start.getMap());
                sb.append(Affichage.GREEN);
                sb.append("Veux tu m'acheter autre chose ?");
                sb.append(Affichage.YELLOW);
                sb.append(" - Y - OUI");
                sb.append(Affichage.YELLOW);
                sb.append("- N - NON");
                System.out.println(sb);
                try {
                    processInput();
                } catch (Exception e) {
                }
            } else {
                System.out.println("Vous n'avez pas assez d'argent mon pauvre, choissez un autre objet\n");
                dialogueBuy();
            }

        }
    }



    /**
     * Permet de voir s'il est possible d'acheter l'item en fonction de la monnaie du joueur
     * @param item l'objet que le joueur veut acheter
     * @return true si l'achat est possible, false sinon
     * @author Gillian
     */
    public boolean checkMoney (AbstractItem item) {
        return item.getPrix()<=Start.getPlayer().getMoney();
    }

    /**
     * Méthode permettant de réinitialiser l'inventaire du marchand et d'ajouter des items aléatoires
     * @param nbWeapons nombre d'armes à ajouter
     * @param nbPotions nombre de potions à ajouter
     * @author Gillian
     */


    public void generateItems (int nbWeapons, int nbPotions) {
        /*
        for (int i = 0; i < nbWeapons; i++) {
            getInventory().getWeapons().add(switch (Procedure.getRandomInt(3, 1)) {
                case 1 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.SWORD);
                case 2 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.WAND);
                default -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.BOW);
            });
        }
        for (int i = 0; i < nbPotions; i++) {
            getInventory().getPotions().add(switch (Procedure.getRandomInt(20, 1)) {
                case 1, 2, 3, 4, 5, 6, 7 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.HEAL_POTION);
                case 8, 9, 10 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.ENDURENCE_POTION);
                case 11, 12, 13 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.INVUL_POTION);
                default -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.STRENGTH_POTION);
            });
        }
         */
        getInventory().getWeapons().add(WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.WAND));
        itemArrayList.addAll(getInventory().getWeapons());
        itemArrayList.addAll(getInventory().getPotions());
    }


    // // ------------------------------------------Vente-------------------------------------------------------------

    /**
     * Dialogue initial quand le marchand veut acheter (que le joueur veut vendre.
     *
     * @author Gillian, Quentin
     */
    public void dialogueSell() {
        state = STATE.SELL;
        if (Start.getPlayer().getInventory().getWeapons().isEmpty())
        {
            state = STATE.SELLPOTIONS;
        }
        if (Start.getPlayer().getInventory().getPotions().isEmpty())
        {
            state = STATE.SELLWEAPONS;
        }
        if (Start.getPlayer().getInventory().getPotions().isEmpty()
                && Start.getPlayer().getInventory().getWeapons().isEmpty())
        {
            state = STATE.SELLEMPTY;
        }

       StringBuilder sb = new StringBuilder();
        sb.append(Affichage.GREEN);
        sb.append("Tu veux donc vendre un de tes modestes objets ? Mhhhhh... J'accepte !\n");

        String appuyer = Affichage.GREEN + "Appuyez sur " + Affichage.YELLOW;
        switch (state) {

            case SELLPOTIONS -> {
                sb.append("Veux tu me vendre une de tes potions ?");
                sb.append(Affichage.GREEN);
                sb.append("Appuyez sur ");
                sb.append(Affichage.YELLOW);
                sb.append(" 'P' \n");
            }
            case SELLWEAPONS -> {
                sb.append("Veux tu me vendre une de tes armes ?");
                sb.append(Affichage.GREEN);
                sb.append("Appuyez sur ");
                sb.append(Affichage.YELLOW);
                sb.append(" 'W' \n");
            }
            case SELLEMPTY -> {
                sb.append(Affichage.RED);
                sb.append("Et bien alors mon pauvre, on a rien à me proposer ?");
                dialogue(false);
            }
            case SELL -> {
                sb.append("Veux tu me vendre une de tes potions ?");
                sb.append(Affichage.GREEN);
                sb.append("Appuyez sur ");
                sb.append(Affichage.YELLOW);
                sb.append(" 'P' \n");
                sb.append("Veux tu me vendre une de tes armes ?");
                sb.append(Affichage.GREEN);
                sb.append("Appuyez sur ");
                sb.append(Affichage.YELLOW);
                sb.append(" 'W' \n");
            }
        }
        System.out.println(sb);

        try {
            processInput();
        } catch (Exception e) { }
    }


    /**
     * Permet d'engager la procédure de vente. Laisse le choix de vendre ou de quitter
     *
     * @param v type d'objet que le joueur veut vendre
     * @throws IOException
     */
    public void procedureSell(int v) throws IOException {
        String type;
        switch (v) {
            case 1 -> {
                type = "armes";
                setState(STATE.SELLWEAPONS);
            }
            case 2 -> {
                type = "potions";
                setState(STATE.SELLPOTIONS);
            }
            default -> {
                type = "none";
                processInput();
            }
        }

        String sb = Affichage.GREEN
                + "Montre moi quelles  !" + Affichage.YELLOW + type + "tu veux me vendre ! \n"
                + "Tu n'as qu'à appuyer sur la touche" +
                Affichage.YELLOW + " 'V' " + Affichage.RESET + Affichage.GREEN + Affichage.BOLD +
                "pour me vendre l'objet que tu tiens dans la main !\n"
                + "Tu peux également me laisser tranquille et t'en aller en appuyant sur " + Affichage.YELLOW + " '4' ";

        System.out.println(sb);
        try {
            processInput();
        } catch (Exception e) {
        }


    }

    /**
     * Permet de vendre un objet en fonction du type de vente du marchand
     * @author Gillian
     */
    public void selling() {
        AbstractItem item;

        switch (getState()) {
            case SELLPOTIONS -> {
                item = Start.getPlayer().getInventory().getPotions().get(0);
                Start.getPlayer().getInventory().sellPotion();
            }
            case SELLWEAPONS -> {

                item = Start.getPlayer().getInventory().getWeapons().get(0);
                Start.getPlayer().getInventory().sellWeapon();
            }
            default -> item = Start.getPlayer().getInventory().getWeapons().get(0);
        }

        state = STATE.SELLCONFIRMATION;

        StringBuilder sb = new StringBuilder();
        sb.append("Félicitations, tu viens de me vendre ");
        sb.append(Affichage.YELLOW);
        sb.append(item.getNom());
        sb.append(Affichage.GREEN);
        sb.append(" pour la belle somme de ");
        sb.append(Affichage.YELLOW);
        sb.append(item.getPrix());
        sb.append("\n \n");
        sb.append(Start.getMap());
        sb.append(Affichage.GREEN);
        sb.append("Veux tu me vendre autre chose ?");
        sb.append(Affichage.YELLOW);
        sb.append(" - Y - OUI");
        sb.append(Affichage.YELLOW);
        sb.append("- N - NON");
        System.out.println(sb);
        try {
            processInput();
        } catch (Exception e) {
            dialogueError();
        }
    }


    // ------------------------------------------Aggressif-------------------------------------------------------------
    /**
     * Passage du marchand en mode agressif lorsque le joueur a décidé d'attaquer le marchand.
     *
     * @author Gillian, Quentin
     */
    public void typeAggressive() {
        //TODO le faire attaquer
        state = STATE.AGGRESSIVE;
        TourManager.addMessage(Affichage.YELLOW +
                "Vous auriez pu choisir la fortune... " +
                Start.getPlayer().getNom() +
                "\n" + Affichage.RED +
                "Mais vous avez choisi la " +
                Affichage.BRIGTH_RED + "MORT" + Affichage.RED + " !!!!");

    }

    // ------------------------------------------Méthodes utilitaires-------------------------------------------------------------

    /**
     * Permet de quitter/commencer le dialogue du marchand.
     * @author Quentin
     */
    public void changeDialogueState() {
        if(state != STATE.AGGRESSIVE){
            state = STATE.VISITED;
        }
        TourManager.Pause();
        TourManager.InDialogue();
    }

    /**
     * Renvoit l'etat du marchand.
     *
     * @return Etat
     * @author Gillian
     */
    public STATE getState() {
        return state;
    }

    /**
     * Fixe l'état du marchand
     *
     * @param state
     * @author Gillian
     */
    public void setState(STATE state) {
        this.state = state;
    }

    @Override
    public String toString() {
        //TODO Changer le smiley s'il est énervé
        if (System.getProperty("os.name").equals("Linux")) {
            return "\uD83D\uDC73";
        } else {
            return Affichage.YELLOW + Affichage.BOLD + Affichage.ITALIC + "£";
        }
    }




    /**
     * Redirection lorsque le joueur répond "yes" aux questions du marchand.
     *
     * @author Gillian
     */
    /*
    public void procedureYes() {
        switch (state) {
            case BUY -> dialogueBuy();
            case SELL -> dialogueSell();
        }
    }

     */

    /**
     * Redirection et changement de mode du marchand lorsque le joueur répond "no" aux questions du marchand.
     *
     * @author Gillian
     */
    /*
    public void procedureNo() {
        state = STATE.VISITED;
        dialogue(true);
    }
    */


    public void dialogueError() {
        //TODO elle sert a rien cette methode
        System.out.println("Cher Monsieur, Chère Madame" + Start.getPlayer().getNom() + "\nJe crois que vous n'avez pas bien compris ma question");
        switch (state) {
            case VISITED -> dialogue(true);
            case BUY -> dialogueInitBuy();
            case SELL -> dialogueSell();
        }
        dialogue(true);
    }


}