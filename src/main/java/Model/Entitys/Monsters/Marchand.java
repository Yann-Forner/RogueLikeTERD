package Model.Entitys.Monsters;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//TODO Faut changer la salle en fonction du statut du marchand
//TODO Système de vente
//TODO Système d'achat
//TODO déplacer money dans inventory

/**
 * Classe décrivant le marchand (considéré donc comme un monstre
 * @author Gillian, Quentin
 */
public class Marchand extends AbstractMonster {

    /**
     * Différents états du marchands
     * @author Gillian
     */
    public enum STATE {
        NOTVISITED, VISITED, BUY, SELL, AGGRESSIVE, SELLWEAPONS, SELLPOTIONS
    }

    private STATE state;


    /**
     * Constructeur du marchand
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
    }

    /**
     * Lance la procédure du marchand lorsque l'on marche dessus (en fonction de son état).
     * @param pv Points de vie
     * @param limited
     * @return boolean
     * @author Gillian, Quentin
     */
    @Override
    public boolean updatePV(int pv, boolean limited) {
        switch (state){
            case  NOTVISITED, VISITED -> dialogue();
            case BUY -> System.out.println("buy"); //TODO
            case AGGRESSIVE -> System.out.println("agrressive"); //TODO
            case SELL -> System.out.println("sell");
        }
        return true;
    }

    /**
     * Dialogue de "bienvenue" lancé lorsque l'on a jamais vu le marchand.
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
     * Dialogue de déclaration des options de vente / d'achat / d'attaque
     * @author Gillian, Quentin
     */
    public void dialogue() {
        changeDialogueState();
        StringBuilder sb = new StringBuilder();
        switch (state){
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
        } catch (Exception e) {}
    }

    /**
     * Analyse de l'entrée utilisateur et redirection vers les différentes procédures.
     * @throws IOException Si le reader ne fonctionne pas
     * @author Gillian, Quentin
     */
    private void processInput() throws IOException {
        //TODO Comment limiter aux actions voulues (car pour l'instant dans la procédure d'achat il peut vendre
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        switch (reader.readLine()) {
            case "1" -> dialogueBuy();
            case "2" -> dialogueSell();
            case "3" -> typeAggressive();
            case "w", "W" -> procedureSell(1);
            case "p", "P" -> procedureSell(2);
            default -> processInput();
        }
        changeDialogueState();
    }




    /**
     * Procédure d'attaque lorsque le marchand est aggressif.
     * @author Gillian, Quentin
     */
    public void updateMonster() {
        if (state == STATE.AGGRESSIVE) {
            super.updateMonster();
        }
    }

    /**
     * Dialogue initial quand le marchand est en mode "vente" (que le joueur veut acheter.
     * @author Gillian, Quentin
     */
    public void dialogueBuy(){
        state = STATE.BUY;
        String sb = Affichage.GREEN +
                "Tu veux donc acquérir un de mes merveilleux objets ? EHEH ! ça me va !\n" +
                "Voici tous mes beaux objets !\n" +
                "Tu n'as qu'à avancer sur l'un d'eux pour me l'acheter !\n";
        System.out.println(sb);

        //TODO ne pas les mettre au sol sa va poser probleme -> Les lister plutot
        System.exit(1);
    }


    /**
     * Dialogue initial quand le marchand veut acheter (que le joueur veut vendre.
     * @author Gillian, Quentin
     */
    public void dialogueSell(){
        //TODO Choisir type d'item à vendre
        //

        state = STATE.SELL;
        String sb = Affichage.GREEN
                + "Tu veux donc vendre un de tes modestes objets ? Mhhhhh... J'accepte !\n"
                + "Préfères tu me vendre une de tes armes ? "  + Affichage.GREEN + "Appuyez sur " + Affichage.YELLOW + " 'W' \n"
                + "Ou plutôt une de tes potions ? "  + Affichage.GREEN + "Appuyez sur " + Affichage.YELLOW + " 'P' \n";

        System.out.println(sb);

        try {
            processInput();
        } catch (Exception e) {}

        System.exit(1);
    }


    /**
     * Permet d'engager la procédure de vente. Laiise le choix de vendre ou de quitter
     * @param v     type d'objet que le joueur veut vendre
     * @throws IOException
     */
    public void procedureSell (int v) throws IOException {
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
                +"Montre moi quelles  !" + Affichage.YELLOW  + type + "tu veux me vendre ! \n"
                +"Tu n'as qu'à appuyer sur la touche" +
                Affichage.YELLOW + " 'V' " + Affichage.RESET + Affichage.GREEN + Affichage.BOLD +
                "pour me vendre l'objet que tu tiens dans la main !\n"
                + "Tu peux également me laisser tranquille et t'en aller en appuyant sur " +   Affichage.YELLOW + " '4' ";

        try {
            processInput();
        } catch (Exception e) {}


    }

    /**
     * Permet de vendre un objet en fonction du type de vente du marchand
     * @author Gillian
     */
    public void selling ()
    {
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

        StringBuilder sb = new StringBuilder();
        sb.append("Félicitations, tu viens de me vendre ");
        sb.append(Affichage.YELLOW);
        sb.append(item.getNom());
        sb.append(Affichage.GREEN);
        sb.append(" pour la belle somme de ");
        sb.append(Affichage.YELLOW);
        sb.append(item.getPrix());

        dialogueSell();


    }


    public void sellConfirmation()
    {

    }


    /**
     * Passage du marchand en mode agressif lorsque le joueur a décidé d'attaquer le marchand.
     * @author Gillian, Quentin
     */
    public void typeAggressive() {
        //TODO le faire attaquer

        state = STATE.AGGRESSIVE;
        TourManager.addMessage(Affichage.YELLOW+
                "Vous auriez pu choisir la fortune... " +
                Start.getPlayer().getNom() +
                "\n" + Affichage.RED +
                "Mais vous avez choisi la "+
                Affichage.BRIGTH_RED + "MORT" + Affichage.RED + " !!!!");

    }

    /**
     * Permet de quitter/commencer le dialogue du marchand.
     * @author Quentin
     */
    public void changeDialogueState(){
        TourManager.Pause();
        TourManager.InDialogue();
    }

    /**
     * Renvoit l'etat du marchand.
     * @return Etat
     * @author Gillian
     */
    public STATE getState(){
        return state;
    }

    /**
     * Fixe l'état du marchand
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



    //------------------------------------------TOUT lES TRUCS A REVOIR----------------------------------------------


    /*
    public ArrayList<AbstractItem> getItems(){
        //TODO implementer ça #GILLIAN
        return null;
    }
    */

    // lui parler
    // acheter des trucs


    /**
     * Redirection lorsque le joueur répond "yes" aux questions du marchand.
     * @author Gillian
     */
    public void procedureYes() {
        switch (state) {
            case BUY -> dialogueBuy();
            case SELL -> dialogueSell();
        }
    }

    /**
     * Redirection et changement de mode du marchand lorsque le joueur répond "no" aux questions du marchand.
     * @author Gillian
     */
    public void procedureNo() {
        dialogue();
        state = STATE.VISITED;
    }


    /**
     * Réalisation de la vente par le joueur.
     * @author Gillian
     */
    public void sellItem() {
        var first = getInventory().getPotions().remove(getInventory().getPotions().size() - 1);
        Start.getPlayer().addMoney(first.getPrix());

        TourManager.addMessage("Tu viens de gagner " + first.getPrix() + "!"
                + "\n \n"
                + Affichage.GREEN + " Veux-tu me vendre autre chose ?"
                + "\n"
                + Affichage.GREEN + "Y - OUI"
                + "\n"
                + Affichage.YELLOW + "N - NON"
        );
        try {
            processInput();
        } catch (Exception e) {
            dialogueError();
        }

    }



//TODO compteur à erreur : si trop attaquer
    //TODO vendre

    /**
     * Dialogue lorsque le joueur a entré un mauvais caractère.
     * Renvoie directement au dialogue correspondant.
     * @author Gillian
     */
    public void dialogueError() {
        //TODO elle sert a rien cette methode
        System.out.println("Cher Monsieur, Chère Madame" + Start.getPlayer().getNom() + "\nJe crois que vous n'avez pas bien compris ma question");
        switch (state) {
            case VISITED -> dialogue();
            case BUY -> dialogueBuy();
            case SELL -> dialogueSell();
        }
        dialogue();
    }


}