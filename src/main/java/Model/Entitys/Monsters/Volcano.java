package Model.Entitys.Monsters;

import Model.Entitys.Entity;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;

import java.util.ArrayList;

/**
 * Volcan qui fait des degats a toutes les entités étants trop proches.
 * @author Quentin
 */
public class Volcano extends AbstractMonster {
    private final ArrayList<Position> adjacents;

    public Volcano(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, Tools.PathType path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type, lvl);
        adjacents=getAdjacents(pos,(int)vision_radius);
    }

    /**
     * Renvoit l'arrayList des cellules adjacentes.
     * @param position Position
     * @param vr Champs de vision
     * @return Cellules adjacentes
     * @author Quentin
     */
    private ArrayList<Position> getAdjacents(Position position, int vr) {
        Etage etage = getEtage();
        Position offset = etage instanceof Room ? ((Room) etage).getAbsolutePos() : new Position(0,0);
        ArrayList<Position> adjacents = new ArrayList<>();
        for (int x = position.getX() - vr; x < position.getX() + vr * 2 - 1; x++) {
            for (int y = position.getY() - vr; y < position.getY() + vr * 2 - 1; y++) {
                if(x>=0 && x< etage.getWidth() && y>=0 && y< etage.getHeigth()){
                    Position pos = new Position(x, y);
                    if (position.Distance(pos) <= vr) {
                        Cell c = etage.get(pos);
                        if (c.getType().equals(Cell.Style.CellType.NORMAL)) {
                            adjacents.add(pos.somme(offset));
                            c.updateCell(c.isAccesible(), new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.RED, "~"));
                        }
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
            return super.toString() + "V";
        }
    }
}
