package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Utils.Affichage;
import Model.Utils.Start;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

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

    public boolean updatePV(int pv){
        this.pv = getPv() + pv;
        String nom_lvl = getNom() + (this instanceof AbstractMonster ? Affichage.BRIGTH_GREEN+Affichage.BOLD+"["+lvl+"]"+Affichage.RESET : "");
        if(getPv()<=0){
            TourManager.addMessage(Affichage.BRIGTH_RED + nom_lvl + Affichage.BRIGTH_RED + " est mort.");
            if (this instanceof AbstractMonster) {
                getEtage().removeMonster((AbstractMonster) this);
                Start.getPlayer().addExp(getExp());
            } else {
                Affichage.getMap(Start.getMap());
                System.out.println("\nFin de la partie.");
                Start.end();
            }
        }
        else {
            if (this instanceof AbstractMonster) {
                TourManager.addMessage(pv > 0 ? Affichage.YELLOW + nom_lvl + Affichage.YELLOW + " s'est soigné de " + pv + "pv." : Affichage.YELLOW + nom_lvl + Affichage.YELLOW + " n'a plus que " + getPv() + "pv.");
            }
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
                    Entity e = cell.getEntity();
                    if(e instanceof AbstractAlive){
                        ((AbstractAlive)e).updatePV(-getForce());
                    }
                    else if(e instanceof AbstractItem) {
                        ((AbstractItem)e).pickupItem();
                    }
                }
            }
            else{
                //TODO faire un bruit de colision
            }
        }
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

}