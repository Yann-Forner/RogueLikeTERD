package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import org.json.JSONObject;

public abstract class AbstractAlive extends Entity {
    private final double vision_radius;
    private int pv;
    private int force;
    protected int lvl;
    private final Inventory inventory = new Inventory();

    private AbstractAlive(Etage m, Position pos, double vr, String nom) {
        super(m, pos, nom);
        vision_radius = vr;
    }

    public AbstractAlive(Etage m, Position pos, double vr, String nom, int pv, int force, int lvl){
        this(m,pos,vr,nom);
        this.pv=pv*lvl;
        this.force=force*lvl;
        this.lvl=lvl;
    }

    public abstract void death();
    public abstract void updatePVMessage();

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
        updatePV(-((AbstractAlive)e).getForce());
    }

    public int getPv(){
        return pv;
    }

    public int getForce(){
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public double getVision_radius() {
        return vision_radius;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getExp(){
        return lvl*5;
    }

    public int getLvl(){
        return lvl;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("Entity",super.toJSON());
        json.put("vision_radius",vision_radius);
        json.put("pv",pv);
        json.put("force",force);
        json.put("lvl",lvl);
        //json.put("inventory",inventory.toJSON());
        return json;
    }
}