package Model.Entitys;

import Model.Entitys.Inventaires.Inventory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Utils.Affichage;
import Model.Utils.Start;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public abstract class Entity {
    private  Position position;
    private Etage etage;
    private final double vision_radius;
    //STATS
    private int pv;
    private int force;
    protected int lvl;
    private final String nom;
    private final Inventory inventory = new Inventory();

    private Entity(Etage m, Position pos, double vr, String nom) {
        position = pos;
        etage = m;
        vision_radius = vr;
        this.nom=nom;
    }

    public Entity(Etage m, Position pos, double vr, String nom, int pv, int force, int lvl){
        this(m,pos,vr,nom);
        this.pv=pv;
        this.force=force;
        this.lvl=lvl;
    }

    public boolean updatePV(int pv){
        this.pv = this.pv + pv;
        String nom_lvl = nom+Affichage.BRIGTH_GREEN+Affichage.BOLD+"["+lvl+"]"+Affichage.RESET;
        if(this.pv<=0){
            TourManager.addMessage(Affichage.BRIGTH_RED + nom_lvl + Affichage.BRIGTH_RED + " est mort.");
            if (this instanceof AbstractMonster) {
                etage.removeMonster((AbstractMonster) this);
                Start.getPlayer().addExp(getExp());
            } else {
                Affichage.getMap(Start.getMap());
                System.out.println("\nFin de la partie.");
                System.exit(0);
            }
        }
        else {
            if (this instanceof AbstractMonster) {
                TourManager.addMessage(pv > 0 ? Affichage.YELLOW + nom_lvl + Affichage.YELLOW + " s'est soigné de " + pv + "pv." : Affichage.YELLOW + nom_lvl + Affichage.YELLOW + " n'a plus que " + getPv() + "pv.");
            }
        }
        return this.pv>0;
    }

    public void move(Position pos) {
        if(pos!=null){
            Cell cell = etage.get(pos);
            if(cell.isAccesible()){
                if(cell.getEntity()==null){
                    cell.setEntity(this);
                    etage.get(position).setEntity(null);
                    position=pos;
                }
                else{
                    cell.getEntity().updatePV(-1);
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

    public double getVision_radius() {
        return vision_radius;
    }

    public String getNom(){
        return nom;
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

    public abstract String toString();
}