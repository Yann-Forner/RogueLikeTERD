package Model.Entitys.Monsters;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Ghost, se déplace à travers les murs
 * @author Quentin
 */
public class Ghost extends AbstractMonster {
    public Ghost(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type, lvl);
    }

    @Override
    public void move(Position pos) {
        if(pos!=null){
            Cell cell = getEtage().get(pos);
            if(cell.getEntity()==null){
                cell.setEntity(this);
                getEtage().get(getPosition()).setEntity(null);
                setPosition(pos);
            }
            else{
                cell.getEntity().onContact(this);
            }
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC7B";
        }
        else{
            return super.toString() + "H";
        }
    }
}
