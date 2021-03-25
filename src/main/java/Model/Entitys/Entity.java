package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;

public abstract class Entity {
    private Position position;
    private Etage etage;
    //STATS
    private int pv;
    private int deplacement;
    private int force;
    private final Inventory inventory = new Inventory();

    protected Entity(Etage m, Position pos) {
        position = pos;
        etage = m;
    }

    public Entity(Etage m, Position pos, int pv, int deplacement, int force){
        this(m,pos);
        this.pv=pv;
        this.deplacement=deplacement;
        this.force=force;
    }

    public abstract void updateEntity(Etage etage, BasicPlayer mainPLayer);

    public boolean updatePV(int pv){
        this.pv = this.pv + pv;
        return this.pv>0;
    }

    public int getPv(){
        return pv;
    }

    public int getDeplacement(){
        return deplacement;
    }

    public int getForce(){
        return force;
    }

   // public abstract void update();

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
        if(cell.isAccesible()){
            cell.setEntity(this);
            etage.get(position).setEntity(null);
            position=pos;
        }
    }

    public void moveLeft() {
        move(position.somme(-1,0));
    }

    public void moveRight() {
        move(position.somme(1,0));
    }

    public void moveUp() {
        move(position.somme(0,-1));
    }

    public void moveDown() {
        move(position.somme(0,1));
    }

    public abstract String toString();
}