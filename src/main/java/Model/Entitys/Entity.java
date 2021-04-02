package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public abstract class Entity {
    private Position position;
    private Etage etage;
    private final int vision_radius;
    //STATS
    private int pv;
    private int force;
    private final String nom;
    private final Inventory inventory = new Inventory();

    private Entity(Etage m, Position pos, int vr, String nom) {
        position = pos;
        etage = m;
        vision_radius = vr;
        this.nom=nom;
        etage.get(position).setEntity(this);
    }

    public Entity(Etage m, Position pos, int vr, String nom, int pv, int force){
        this(m,pos,vr,nom);
        this.pv=pv;
        this.force=force;
    }

    public boolean updatePV(int pv){
        this.pv = this.pv + pv;
        if(this.pv<=0){
            TourManager.AddMessage(nom+" est mort.");
            if (this instanceof AbstractMonster) {
                etage.removeMonster((AbstractMonster) this);
            } else {
                System.out.println("Fin de la partie.");
                System.exit(0);
            }
        }
        TourManager.AddMessage(pv>0 ? nom+" s'est soigné de "+pv+"pv." : nom+" a perdu "+Math.abs(pv)+"pv.");
        return this.pv>0;
    }

    public int getPv(){
        return pv;
    }

    public int getForce(){
        return force;
    }

    public Position getPosition() {
        return position.copyOf();
    }

    public void setPosition(Position pos){
        position=pos;
    }

    public Etage getEtage(){
        return etage;
    }

    public void setEtage(Etage etage){
        this.etage=etage;
    }

    public void move(Position pos) {
        Cell cell = etage.get(pos);
        if(cell.isAccesible() || this instanceof AbstractMonster){
            if(cell.getEntity()==null){
                cell.setEntity(this);
                etage.get(position).setEntity(null);
                position=pos;
            }
            else{
                cell.getEntity().updatePV(-1);
                //TourManager.pause();
            }
        }
    }

    public int getVision_radius() {
        return vision_radius;
    }

    public String getNom(){
        return nom;
    }

    public abstract String toString();
}