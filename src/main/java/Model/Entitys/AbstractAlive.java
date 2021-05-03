package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

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
     * @param pv int
     * @return True si l'entité est toujours en vie
     * @author Quentin
     */
    public boolean updatePV(int pv){
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
     * @author Yann
     */
    public void move(Position pos) {
        if(pos!=null){
            Cell cell = getEtage().get(pos);
            if(cell.isAccesible()){
                if(cell.getEntity()==null){
                    cell.setEntity(this);
                    getEtage().get(getPosition()).setEntity(null);
                    setPosition(pos);
                }
                else{
                    cell.getEntity().onContact(this);
                }
            }
            else{
                //TODO faire un bruit de colision
            }
        }
    }

    @Override
    public void onContact(Entity e) {
        //TODO empecher le CaC si la currentWepaon est une arme de distance
        if(e instanceof BasicPlayer){
            BasicPlayer player = (BasicPlayer) e;
            AbstractWeapon currentWeapon = player.getInventory().getCurrentWeapon();
            player.updateEndurence(- (currentWeapon == null ? 1 : currentWeapon.getCoutEndurence()));
        }
        updatePV(((AbstractAlive)e).caC());
    }

    /**
     * Defini les pv enlévé lors d'un Corps a corps.
     * @return Points de vie en moins
     * @author Quentin
     */
    public int caC(){
        return -this.getForce();
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

    @Override
    public String toString() {
        return Affichage.GREEN;
    }
}