package Model.Entitys.Monsters;

import Model.Entitys.Entity;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

import java.util.ArrayList;


public class Volcano extends AbstractMonster {
    private final ArrayList<Position> adjacents;

    public Volcano(Etage m, Position pos,String nom, int pv, int force, double vision_radius , int agro, int update_rate, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type, lvl);
        adjacents=getAdjacents(pos,(int)vision_radius);
    }

    private ArrayList<Position> getAdjacents(Position position, int vr) {
        ArrayList<Position> adjacents = new ArrayList<>();
        int posX = position.getX();
        int posY = position.getY();
        for (int x = posX - vr; x < posX + vr * 2 - 1; x++) {
            for (int y = posY - vr; y < posY + vr * 2 - 1; y++) {
                Position pos = new Position(x, y);
                if (position.Distance(pos) <= vr) {
                    Cell c = getEtage().get(pos);
                    if (c.getType().equals(Cell.Style.CellType.NORMAL)) {
                        adjacents.add(pos);
                        c.updateCell(c.isAccesible(), new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_RED, "~"));
                    }
                }
            }
        }
        return adjacents;
    }

    @Override
    public void updateMonster() {
        for(Position p : adjacents){
            Entity e = getEtage().get(p).getEntity();
            if(e!=null && e!=this){
                e.onContact(this);
            }
        }
    }

    @Override
    public boolean updatePV(int pv) {
        return true;
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83C\uDF0B";
        }
        else{
            return Affichage.RED+Affichage.BOLD+"V";
        }
    }
}
