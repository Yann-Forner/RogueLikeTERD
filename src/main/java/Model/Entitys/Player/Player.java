package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.Classes.AbstractClass;
import Model.Map.Etage;
import Model.Utils.*;
import Model.Utils.Position.Direction;

/**
 * Classe de base du joueur
 * @author Quentin, Yann, Gillian
 */
public class Player extends AbstractAlive {
    private int MAX_EXP = 10;
    private int CURRENT_EXP = 0;
    private int MAX_PV;
    private final int MAX_FORCE;
    private long MovementCoolDown = System.currentTimeMillis();
    private final AbstractClass classe;
    private Direction direction = Direction.HAUT;
    private int endurence = 100;
    private final AbstractItem[] poche = {null,null};
    private boolean inPoche = false;
    private boolean buffForce = false;
    private boolean buffInvincible = false;
    private boolean buffEnergie = false;

    /**
     * Crée un joueur.
     * @param nom    Nom
     * @param classe Classe du joueur
     * @author Quentin
     */
    public Player(String nom, AbstractClass classe) {
        super(null, null, classe.getVisionRadius(), nom.length() == 0 ? classe.getNom() : nom, classe.getBasePV(), classe.getBaseForce(), 1);
        this.classe = classe;
        MAX_PV = classe.getBasePV();
        MAX_FORCE = classe.getBaseForce();
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
        position = position == null ? Procedure.getAccesibleRandomPosition(true,etage) : position;
        setPosition(position);
        etage.get(position).setEntity(this);
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
            updatePV(MAX_PV / 2,true);
            MAX_PV *= 1.5;
            lvl++;
            TourManager.addMessage(Affichage.BRIGTH_CYAN + "Vous avez gagné un niveau");
            Sound.playAudio(Sound.Sons.LEVELUP,0);
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
     * @return Exp actuelle
     * @author Quentin
     */
    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    /**
     * Deplace le joueur dans cette direction.
     * @param dir Direction
     * @author Quentin
     */
    public void moveDirection(Direction dir){
        if (System.currentTimeMillis() - MovementCoolDown > classe.getSpeed()) {
            Position oldPos = getPosition();
            Position newPos = getPosition().somme(dir.getVecteur());
            move(newPos);
            if(poche[1] != null){
                getEtage().addItem(poche[1]);
                poche[1] = null;
            }
            direction = dir;
            MovementCoolDown = System.currentTimeMillis();
            if(getEtage().get(newPos).isAccesible()){
                videPoche(oldPos);
            }
        }
    }

    /**
     * Change l'endurence du joueur.
     * @param endurence Endurence
     * @author Quentin
     */
    public void updateEndurence(int endurence){
        this.endurence = (endurence < 0 && getBuff(AbstractPotion.Buffs.ENERGIE)) ? this.endurence : Math.min(getMAX_ENDURENCE(), this.endurence + endurence);
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        return getBuff(AbstractPotion.Buffs.INVINCIBLE) ? super.updatePV(Math.max(0, pv), limited) : super.updatePV(pv, limited);
    }

    /**
     * Renvoit l'endurence du joueur.
     * @return Endurence
     * @author Quentin
     */
    public int getEndurence(){
        return endurence;
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
     * Retorune l'endurance maximale
     * @return Endurance
     * @author JP
     */
    public int getMAX_ENDURENCE() {
        return 100;
    }

    @Override
    public int getForce() {
        AbstractWeapon currentWeapon = getInventory().getCurrentWeapon();
        return super.getForce() * lvl + (currentWeapon==null || !classe.canUse(currentWeapon) ? 0 : currentWeapon.getStrength());
    }

    public int getMAX_FORCE(){
        return MAX_FORCE;
    }

    /**
     * Renvoit la direction vers laquelle le joueur regarde.
     * @return Direction
     * @author Quentin
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * Stock l'item dans la poche du joueur.
     * @param item Item a stocker
     * @author Quentin
     */
    public void setPoche(AbstractItem item){
        if(poche[0] != null){
            poche[1] = poche[0];
            poche[1].setPosition(getPosition());
        }
        getEtage().removeItem(item);
        inPoche=true;
        poche[0] = item;
    }

    /**
     * Vide la poche du joueur.
     * @param pos Position de l'item contenu dans la poche
     * @author Quentin
     */
    public void videPoche(Position pos){
        if(inPoche){
            inPoche=false;
        }
        else{
            if(poche[0] != null){
                poche[0].setPosition(pos);
                getEtage().addItem(poche[0]);
                poche[0] = null;
            }
        }
    }

    /**
     * Renvoit le contenu de la poche.
     * @return Contenu de la poche
     * @author Quentin
     */
    public AbstractItem getPoche(){
        return poche[0];
    }

    /**
     * Est appelé si le joueur n'a pas bougé et du coup il ne fait pas enlever l'objet de la poche.
     * @author Quentin
     */
    public void notMovedforPoche(){
        inPoche = true;
    }

    public boolean getBuff(AbstractPotion.Buffs buff){
        return switch (buff){
            case FORCE -> buffForce;
            case ENERGIE -> buffEnergie;
            case INVINCIBLE -> buffInvincible;
        };
    }

    public void setBuff(AbstractPotion.Buffs buff, boolean buffed){
        switch (buff){
            case FORCE -> buffForce = buffed;
            case ENERGIE -> buffEnergie = buffed;
            case INVINCIBLE -> buffInvincible = buffed;
        }
    }

    @Override
    public String toString() {
        return classe.toString();
    }
}
