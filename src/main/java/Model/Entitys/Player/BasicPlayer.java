package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.Classes.AbstractClass;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Sound;
import Model.Utils.TourManager;

/**
 * Classe de base du joueur
 *
 * @author Quentin, Yann, Gillian
 */
public class BasicPlayer extends AbstractAlive {
    private int MAX_EXP = 10;
    private int CURRENT_EXP = 0;
    private int MAX_PV;
    private long MovementCoolDown = System.currentTimeMillis();
    private final AbstractClass classe;
    private int money = 0;
    private Direction direction = Direction.HAUT;
    private int endurence = 100;

    public enum Direction{
        HAUT(new Position(0,-1)),
        BAS(new Position(0,1)),
        DROITE(new Position(1,0)),
        GAUCHE(new Position(-1,0));

        private final Position vecteur;

        Direction(Position vecteur){
            this.vecteur = vecteur;
        }

        public Position getVecteur(){
            return vecteur;
        }
    }

    /**
     * Crée un joueur.
     * @param nom    Nom
     * @param classe Classe du joueur
     * @author Quentin
     */
    public BasicPlayer(String nom, AbstractClass classe) {
        super(null, null, classe.getVisionRadius(), nom.length() == 0 ? classe.getNom() : nom, classe.getBasePV(), classe.getBaseForce(), 1);
        this.classe = classe;
        MAX_PV = classe.getBasePV();
        classe.setBaseItems(this);
    }

    /**
     * Change l'etage et la position du joueur dans celui ci.
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
            Sound.playAudio(Sound.Sons.LEVELUP,0);
        }
    }

    /**
     * Ajoute de l'argent au joueur.
     * @param m Montant de la monnaie à ajouter
     * @author Gillian
     */
    public void addMoney(int m) {
        money += m;

    }

    /**
     * Enlève de l'argent au joueur
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
        Sound.playAudio(Sound.Sons.MORT,0);
        TourManager.addMessage(Affichage.BRIGTH_RED + getNom() + Affichage.BRIGTH_RED + " est mort.\n");
        TourManager.Pause();
        Affichage.getMap(true);
        Affichage.end();
        try {
            Thread.sleep(1500);
        }
        catch (InterruptedException ignored) { }
        System.exit(0);
    }

    @Override
    public void updatePVMessage() {
        Sound.playAudio(Sound.Sons.DEGATSJOUEUR,0);
    }

    /**
     * Renvoit la classe du joueur.
     * @return Classe
     * @author Quentin
     */
    public AbstractClass getClasse() {
        return classe;
    }

    /**
     * Renvoit l'experience necessaire pour monter de niveau.
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
     * @author Yann, Quentin
     */
    public void moveLeft() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(-1, 0));
            direction=Direction.GAUCHE;
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule a sa droite.
     * @author Yann, Quentin
     */
    public void moveRight() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(1, 0));
            direction=Direction.DROITE;
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le haut.
     * @author Yann, Quentin
     */
    public void moveUp() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(0, -1));
            direction=Direction.HAUT;
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le bas.
     * @author Yann, Quentin
     */
    public void moveDown() {
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            move(getPosition().somme(0, 1));
            direction=Direction.BAS;
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    /**
     * Change l'endurence du joueur.
     * @param endurence Endurence
     * @author Quentin
     */
    public void updateEndurence(int endurence){
        this.endurence = this.endurence + endurence;
    }

    /**
     * Renvoit l'endurence du joueur.
     * @return Endurence
     * @author Quentin
     */
    public int getEndurence(){
        return endurence;
    }

    /**
     * Modifie l'endurance
     * @param endurence Endurance
     */
    public void setEndurence(int endurence) {
        this.endurence = endurence;
    }

    @Override
    public int getPv() {
        return super.getPv();
    }

    /**
     * Renvoit les pv maximum du joueur.
     * @return pv max
     * @author Quentin
     */
    public int getMAX_PV() {
        return MAX_PV;
    }

    /**
     * Retourne le montant de l'argent du joueur
     * @return money
     * @author Gillian
     */
    public int getMoney() {
        return money;
    }

    @Override
    public int getForce() {
        AbstractWeapon currentWeapon = getInventory().getCurrentWeapon();
        return super.getForce() * lvl + (currentWeapon==null ? 0 : currentWeapon.getStrength());
    }

    /**
     * Renvoit la direction vers laquelle le joueur regarde.
     * @return Direction
     * @author Quentin
     */
    public Direction getDirection(){
        return direction;
    }

    @Override
    public String toString() {
        return classe.toString();
    }
}
