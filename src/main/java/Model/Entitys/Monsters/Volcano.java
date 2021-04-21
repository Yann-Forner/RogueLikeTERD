package Model.Entitys.Monsters;

import Model.Entitys.Entity;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;


public class Volcano extends AbstractMonster {

    public Volcano(Etage m, Position pos,String nom, int pv, int force, double vision_radius , int agro, int update_rate, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type, lvl);
    }

    @Override
    public void updateMonster() {
        //TODO ne pas recalculer a chaque fois mais stocker les cases adj
        int posX = getPosition().getX();
        int posY = getPosition().getY();
        for(int x = posX - (int)getVision_radius(); x < posX + getVision_radius()*2 -1; x++) {
            for (int y = posY - (int)getVision_radius(); y < posY + getVision_radius()*2 -1; y++) {
                if(getPosition().Distance(new Position(x,y))<=getVision_radius()){
                    Cell c = getEtage().get(x, y);
                    if(c.getType().equals(Cell.Style.CellType.NORMAL)){
                        c.updateCell(c.isAccesible(), new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.BRIGTH_RED,"~"));
                    }
                    Entity e= c.getEntity();
                    if(e!=null && e!=this){
                        e.onContact(this);
                    }
                }
            }
        }
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
