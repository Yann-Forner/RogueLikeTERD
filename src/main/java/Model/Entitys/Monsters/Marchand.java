package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



//TODO Faut changer la salle en fonction du statut du marchand
//TODO Système de vente
//TODO Système d'achat
public class Marchand extends AbstractMonster {


    public enum STATE {
        NOTVISITED, VISITED, BUYER, SELLER, AGGRESSIVE
    }

    STATE state;


    public Marchand(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate, int pathCross, int lvl, STATE state) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
        this.state = state;
    }

    @Override
    public String toString() {
        //TODO Changer le smiley s'il est énervé

        if (System.getProperty("os.name").equals("Linux")) {
            return "\uD83D\uDC73";
        } else {
            return Affichage.YELLOW + Affichage.BOLD + "M";
        }
    }


    /**
     *
     * @param pv
     * @return boolean
     */
    @Override
    public boolean updatePV(int pv) {
        if (state == STATE.NOTVISITED) {
            dialogueInit();
        } else if (state == STATE.VISITED) {
            dialogue();
        } else if (state == STATE.BUYER) {
            //TODO
        } else if (state == STATE.SELLER) {
            //TODO
        } else if (state == STATE.AGGRESSIVE) {
            super.updatePV(pv);
        }
        return true;
    }

    public void dialogueInit() {

        TourManager.addMessage("Bonjour" + getNom()
                + "\n"
                + "Bienvenue chez le plus grand, le pLus beau, le plus charismatique marchand de tout le labyrinthe."
                + "\n"
                + "Cela fait maintenant près de 10 ans que je suis bloqué dans ce labyrinthe. Je suis donc l'homme le plus riiiiche que tu puisses trouver ici "
                + "\n"
                + "Tu te doutes bien que toute cette fortune n'est pas sortie de nulle part !"
                + "\n"
                + "Je vends, j'achète, je vo... enfin bref, je mène mon buisness quoi !"
                + "\n \n"
        );

        dialogue();


    }

    public void dialogue() {

        if (state == STATE.VISITED) {
            TourManager.addMessage("Comme on se retrouve !" + "\n \n");
        } else if (state == STATE.NOTVISITED) {
            state = STATE.VISITED;
        }

        TourManager.addMessage(
                "Tu es ici pour acheter un de mes merveilleux objets ou pour me vendre un des tiens ?"
                        + "\n \n"
                        + Affichage.GREEN + "1 - Acheter"
                        + "\n"
                        + Affichage.YELLOW + "2 - Vendre"
                        + "\n"
                        + Affichage.RED + " 3 - Attaquer"
        );

        try {
            processInput();
        } catch (Exception e) {
            dialogueError();
        }
    }

    public void dialogueError() {
        TourManager.addMessage("Cher Monsieur, Chère Madame" + getNom()
                + "\n"
                + "Je crois que vous n'avez pas bien compris ma question"
        );
        dialogue();
    }


//TODO compteur à erreur : si trop attaquer
    //TODO vendre


    private void processInput() throws IOException {
        char cmd = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (System.console() != null) {
            char[] input = System.console().readPassword();
            if (input.length > 0) {
                cmd = input[0];
            }
        } else {
            String input = reader.readLine();
            if (input.length() > 0) {
                cmd = input.charAt(0);
            }
        }
        switch (cmd) {
            case '1' -> typeBuy();
            case '2' -> typeSell();
            case '3' -> typeAggressive();
            default -> processInput();
        }
    }


    public void updateMonster() {

        if (state == STATE.AGGRESSIVE) {
            double vision_radius = Alert > 0 ? Agro : getVision_radius();
            if (Start.getPlayer().getPosition().Distance(getPosition()) <= vision_radius) {
                if (Alert == 0) {
                    TourManager.addMessage(getNom() + " vous a reperé!!!");
                }
                Alert = Agro;
                move(nextPosition());
                Alert--;
            } else {
                Alert = 0;
            }
        }
    }

    /**
     *
     */
    public void typeBuy(){

        TourManager.addMessage("Tu veux donc acquérir un de mes merveilleux objets ? EHEH ! ça me va !"
                + "\n"
                + Affichage.GREEN + " Voici tous mes beaux objets !"
                + "\n"
                + " Tu n'as qu'à avancer sur l'un d'eux pour me "+ Affichage.GREEN + " l'acheter !"
        );
        //TODO AJOUTER LES ITEMS AU SOL
        //getEtage().addItem();


    }


    public void typeSell(){



    }


    public void typeAggressive(){

        TourManager.addMessage("Vous auriez pu choisir la fortune... " + getNom()
                + "\n"
                + Affichage.RED + " Mais vous avez choisi la MORT !"
        );
        state = STATE.AGGRESSIVE;
    }


    // lui parler
    // acheter des trucs


}
