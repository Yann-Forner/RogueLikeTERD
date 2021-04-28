package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Player.Classes.AbstractClass;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

/**
 * Classe de base du joueur
 *
 * @author Quentin, Yann, Gillian
 */
public class BasicPlayer extends AbstractAlive {
    private int MAX_EXP;
    private int CURRENT_EXP;
    private int MAX_PV;
    private long MovementCoolDown = System.currentTimeMillis();
    private final AbstractClass classe;
    private int money;

    /**
     * Crée un joueur.
     *
     * @param nom    Nom
     * @param classe Classe du joueur
     * @author Quentin
     */
    public BasicPlayer(String nom, AbstractClass classe) {
        super(null, null, classe.getVisionRadius(), nom.length() == 0 ? classe.getNom() : nom, classe.getBasePV(), classe.getBaseForce(), 1);
        this.classe = classe;
        MAX_EXP = 10;
        CURRENT_EXP = 0;
        MAX_PV = classe.getBasePV();
        classe.setBaseItems(this);
        money = 0;
    }

    /**
     * Change l'etage et la position du joueur dans celui ci.
     *
     * @param etage    Etage
     * @param position Position
     * @author Quentin
     */
    public void updateEtage(Etage etage, Position position) {
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    /**
     * Ajoute de l'experience au joueur.
     * Quend celle ci atteint un certain seuil, le joueur monte d'un niveau et elle rappart de 0.
     *
     * @param exp Experience.
     * @author Quentin
     */
    public void addExp(int exp) {
        CURRENT_EXP += exp;
        if (CURRENT_EXP >= MAX_EXP) {
            MAX_EXP *= 2;
            CURRENT_EXP = 0;
            updatePV(MAX_PV / 2);
            MAX_PV *= 1.5;
            lvl++;
            TourManager.addMessage(Affichage.BRIGTH_CYAN + "Vous avez gagné un niveau");
        }
    }

    /**
     * Ajoute de l'argent au joueur.
     *
     * @param m Montant de la monnaie à ajouter
     * @author Gillian
     */
    public void addMoney(int m) {
        money += m;

    }

    /**
     * Enlève de l'argent au joueur
     *
     * @param m Montant de la monnaie à soustraire
     * @return true si possible, false sinon
     * @author Gillian
     */
    public boolean removeMoney(int m) {

        if (money - m < 0) {
            return false;
        } else {
            money -= m;
            return true;
        }

    }

    @Override
    public void death() {
        TourManager.addMessage(Affichage.BRIGTH_RED + getNom() + Affichage.BRIGTH_RED + " est mort.");
        Affichage.getMap();
        System.out.println("\nFin de la partie.");
        Start.end();
    }

    @Override
    public void updatePVMessage() {
    }

    /**
     * Renvoit la classe du joueur.
     *
     * @return Classe
     * @author Quentin
     */
    public AbstractClass getClasse() {
        return classe;
    }

    /**
     * Renvoit l'experience necessaire pour monter de niveau.
     *
     * @return Exp necessaire
     * @author Quentin
     */
    public int getMAX_EXP() {
        return MAX_EXP;
    }

    /**
     * Renvoit l'experience actuelle.
     *
     * @return Exp actuelle
     * @author Quentin
     */
    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    /**
     * Deplace le joueur d'une cellule a sa gauche.
     *
     * @author Yann
     */
    public void moveLeft() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(-1, 0));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule a sa droite.
     *
     * @author Yann
     */
    public void moveRight() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(1, 0));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le haut.
     *
     * @author Yann
     */
    public void moveUp() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(0, -1));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le bas.
     *
     * @author Yann
     */
    public void moveDown() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(0, 1));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    @Override
    public int getPv() {
        return super.getPv();
    }

    /**
     * Renvoit les pv maximum du joueur.
     *
     * @return pv max
     * @author Quentin
     */
    public int getMAX_PV() {
        return MAX_PV;
    }

    /**
     * Retourne le montant de l'argent du joueur
     *
     * @return money
     * @author Gillian
     */
    public int getMoney() {
        return money;
    }

    @Override
    public int getForce() {
        return super.getForce() * lvl;
    }

    @Override
    public String toString() {
        return classe.toString();
    }
}
