package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.Classes.AbstractClass;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Sound;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Classe de base du joueur
 *
 * @author Quentin, Yann, Gillian
 */
public class Player extends AbstractAlive {
    private int MAX_EXP = 10;
    private int CURRENT_EXP = 0;
    private int MAX_PV;
    private long MovementCoolDown = System.currentTimeMillis();
    private final AbstractClass classe;
    private int money = 0;
    private Direction direction = Direction.HAUT;
    private int endurence = 100;
    private int MAX_ENDURENCE = 100;
    private final AbstractItem[] poche = {null,null};
    private boolean inPoche = false;
    private boolean isBuffed;
    private boolean isImmortal;

    public enum Direction{
        //TODO DEPLACER DANS UTIL
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
    public Player(String nom, AbstractClass classe) {
        super(null, null, classe.getVisionRadius(), nom.length() == 0 ? classe.getNom() : nom, classe.getBasePV(), classe.getBaseForce(), 1);
        this.classe = classe;
        MAX_PV = classe.getBasePV();
        isBuffed = false;
        isImmortal = false;
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
            updatePV(MAX_PV / 2,true);
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
        this.endurence = Math.min(getMAX_ENDURENCE(), this.endurence + endurence);
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        return isImmortal() ? super.updatePV(Math.max(0, pv), limited) : super.updatePV(pv, limited);
    }

    public void buffStrength(double buffMultiplicator, int seconds) {
        if(isBuffed() == false) {
            setBuffed(true);
            int originalForce = getForce();
            int forceConverted = (int) (((double) originalForce) * (1.0 + buffMultiplicator / 100.0));
            setForce(getForce() + forceConverted);
            TourManager.addMessage("Pendant " + seconds + "s, la force sera augmentée de " + buffMultiplicator + "%. (" + originalForce + " -> " + forceConverted + ")");

            TourManager.getExecutor().schedule(() -> {
                int previousForce = getForce() - forceConverted;
                setForce(previousForce);
                setBuffed(false);
                TourManager.addMessage("Retour de la force à sa valeur de base. (" + previousForce + ")");
            }, seconds, TimeUnit.SECONDS);
        }
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
     * Redéfinit l'endurance du joueur
     * @param endurence Endurance
     * @author JP
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
     * Retorune l'endurance maximale
     * @return Endurance
     * @author JP
     */
    public int getMAX_ENDURENCE() {
        return MAX_ENDURENCE;
    }

    /**
     * Met à jour l'endurance maximale
     * @param MAX_ENDURENCE Maximum
     * @author JP
     */
    public void setMAX_ENDURENCE(int MAX_ENDURENCE) {
        this.MAX_ENDURENCE = MAX_ENDURENCE;
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
        return super.getForce() * lvl + (currentWeapon==null || !classe.canUse(currentWeapon) ? 0 : currentWeapon.getStrength());
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

    /**
     * Retourne si une potion de force est en cours d'utilisation
     * @return
     * @author JP
     */
    public boolean isBuffed() {
        return isBuffed;
    }

    /**
     * Définit si une potion de force est en cours d'utilisation
     * @param buffed
     * @author JP
     */
    public void setBuffed(boolean buffed) {
        isBuffed = buffed;
    }

    /**
     * Retourne si une potion d'invulnérabilité est en cours d'utilisation
     * @return
     * @author JP
     */
    public boolean isImmortal() {
        return isImmortal;
    }

    /**
     * Définit si une potion d'invulnérabilité est en cours d'utilisation
     * @param immortal
     * @author JP
     */
    public void setImmortal(boolean immortal) {
        isImmortal = immortal;
    }

    @Override
    public String toString() {
        return classe.toString();
    }
}
