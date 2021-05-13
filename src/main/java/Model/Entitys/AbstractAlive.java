package Model.Entitys;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Inventory;
import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.Melee;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.*;

/**
 * Classe abstraite des mobs du jeu, un mob peut se deplacer et mourir.
 * @author Yann, Quentin, JP
 */
public abstract class AbstractAlive extends Entity {
    private final double vision_radius;
    private int pv;
    private int force;
    protected int lvl;
    private final Inventory inventory = new Inventory();

    /**
     * Crée un mob.
     * @param m Etage
     * @param pos Position
     * @param vr Champs de vision de l'entité
     * @param nom Nom
     * @author Quentin
     */
    private AbstractAlive(Etage m, Position pos, double vr, String nom) {
        super(m, pos, nom);
        vision_radius = vr;
    }

    /**
     * Crée un mob avec ses pv sa force et son niveau de depart.
     * @param m Etage
     * @param pos Position
     * @param vr Champs de vision de l'entité
     * @param nom Nom
     * @param pv Points de vie
     * @param force Force
     * @param lvl Niveau
     * @author Quentin
     */
    public AbstractAlive(Etage m, Position pos, double vr, String nom, int pv, int force, int lvl){
        this(m,pos,vr,nom);
        this.pv=pv*lvl;
        this.force=force*lvl;
        this.lvl=lvl;
    }

    /**
     * Defini le comportement de l'entité a sa mort.
     * @author Quentin
     */
    public abstract void death();

    /**
     * Defini le message qui sera affiché lorsque l'entité va modifier ses pv.
     * @author Quentin
     */
    public abstract void updatePVMessage();

    /**
     * Defini ce qu'il se passe lorsque les pv de l'entité changent.
     * @param pv Quantité de pv a ajouté
     * @param limited Defini si les pv ajoutés peuvent depassé le seuil maximal
     * @return True si l'entité est toujours en vie
     * @author Quentin
     */
    public boolean updatePV(int pv, boolean limited){
        if(limited && this instanceof Player){
            int pvMax = ((Player) this).getMAX_PV();
            pv = Math.min(pv, Math.max(pvMax - getPv(),0));
        }
        this.pv = getPv() + pv;
        if(getPv()<=0){
            death();
        }
        else {
            updatePVMessage();
        }
        return getPv()>0;
    }

    /**
     * Deplace l'entité a la position passé en parametre.
     * @param pos Position cible
     * @author Yann, Quentin
     */
    public void move(Position pos) {
        Player player = Start.getPlayer();
        if(player != null && pos != null) {
            Cell cell = getEtage().get(pos);
            if (cell.isAccesible()) {
                Entity entity = cell.getEntity();
                if (entity == null || (entity instanceof AbstractWeapon && inventory.isWeaponsFull()) || (entity instanceof AbstractPotion && inventory.isPotionsFull())) {
                    if (entity != null) {
                        player.setPoche((AbstractItem) entity);
                    }
                    cell.setEntity(this);
                    getEtage().get(getPosition()).setEntity(null);
                    setPosition(pos);
                } else {
                    entity.onContact(this);
                    if(entity instanceof AbstractMonster && getEtage().getMonsters().contains(entity)){
                        player.notMovedforPoche();
                    }
                }
            } else {
                if (this instanceof Player) {
                    Sound.playAudio(Sound.Sons.COLLISION, 0);
                }
            }
        }
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof Player){
            Player player = (Player) e;
            AbstractWeapon currentWeapon = player.getInventory().getCurrentWeapon();
            int force = ((AbstractAlive)e).getForce();
            if(e.getPosition().Distance(getPosition())<=1 && currentWeapon!=null){
                if(!(currentWeapon instanceof Melee)){
                    force = force/2;
                }
            }
            int endurence_requis = (currentWeapon == null ? 1 : currentWeapon.getCoutEndurence());
            if(player.getEndurence() >= endurence_requis){
                player.updateEndurence(- endurence_requis);
                updatePV(-force,false);
            }
        }
        else{
            updatePV( - ((AbstractAlive)e).getForce(),false);
        }
    }

    /**
     * Renvoit ses pv.
     * @return Points de vie
     * @author Quentin
     */
    public int getPv(){
        return pv;
    }

    /**
     * Renvoit sa force.
     * @return Force
     * @author Quentin
     */
    public int getForce(){
        return force;
    }

    /**
     * Defini sa force.
     * @author Quentin
     */
    public void setForce(int force) {
        this.force = force;
    }

    /**
     * Renvoit le champ de vision de l'entité.
     * @return double
     * @author Quentin
     */
    public double getVision_radius() {
        return vision_radius;
    }

    /**
     * Renvoit l'inventaire de l'entité.
     * @return Inventaire.
     * @author JP
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Renvoit l'exp que le joueur va gagner lorsque l'entité va mourir.
     * @return int
     * @author Quentin
     */
    public int getExp(){
        return lvl*5;
    }

    /**
     * Renvoit le niveau de l'entité.
     * @return int
     * @author Quentin
     */
    public int getLvl(){
        return lvl;
    }

    /**
     * Redéfinit la vie
     * @param pv Vie
     * @author JP
     */
    public void setPv(int pv) {
        this.pv = pv;
    }

    @Override
    public String toString() {
        return Affichage.GREEN;
    }
}