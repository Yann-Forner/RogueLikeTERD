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

/**
 * Classe décrivant le marchand (considéré donc comme un monstre
 *
 * @author Gillian
 */
public class Marchand extends AbstractMonster {


    /**
     * Différents états du marchands
     *
     * @author Gillian
     */
    public enum STATE {
        NOTVISITED, VISITED, BUY, SELL, AGGRESSIVE
    }

    /**
     * Champs décrivant l'état du marchand
     *
     * @author Gillian
     */
    STATE state;

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


    @Override
    public boolean updatePV(int pv) {
        if (state == STATE.NOTVISITED) {
            dialogueInit();
        } else if (state == STATE.VISITED) {
            dialogue();
        } else if (state == STATE.BUY) {
            //TODO
        } else if (state == STATE.SELL) {
            //TODO
        } else if (state == STATE.AGGRESSIVE) {
            super.updatePV(pv);
        }
        return true;
    }

    /**
     * Dialogue de "bienvenue" lancé lorsque l'on a jamais vu le marchand
     *
     * @author Gillian
     */
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

    /**
     * Dialogue de déclaration des options de vente / d'achat / d'attaque
     * @author Gillian
     */
    public void dialogue() {
        /*
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
        */
    }

    /**
     * Dialogue lorsque le joueur a entré un mauvais caractère
     * @author Gillian
     */
    public void dialogueError() {
        TourManager.addMessage("Cher Monsieur, Chère Madame" + getNom()
                + "\n"
                + "Je crois que vous n'avez pas bien compris ma question"
        );
        dialogue();
    }


//TODO compteur à erreur : si trop attaquer
    //TODO vendre


    /**
     * Analyse de l'entrée utilisateur et redirection vers les différentes procédures
     * @throws IOException
     */
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


    /**
     * Procédure d'attaque lorsque le marchand est aggressif
     * @author Gillian
     */
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
     *Procédure lorsque le marchand est en mode "vente"
     *
     */
    public void typeBuy() {

        TourManager.addMessage("Tu veux donc acquérir un de mes merveilleux objets ? EHEH ! ça me va !"
                + "\n"
                + Affichage.GREEN + " Voici tous mes beaux objets !"
                + "\n"
                + " Tu n'as qu'à avancer sur l'un d'eux pour me " + Affichage.GREEN + " l'acheter !"
        );

       // getEtage().addItem();
        //TODO AJOUTER LES ITEMS AU SOL
        //getEtage().addItem();


    }


    public void typeSell() {


    }


    public void typeAggressive() {

        TourManager.addMessage("Vous auriez pu choisir la fortune... " + getNom()
                + "\n"
                + Affichage.RED + " Mais vous avez choisi la MORT !"
        );
        state = STATE.AGGRESSIVE;
    }


    // lui parler
    // acheter des trucs


}
