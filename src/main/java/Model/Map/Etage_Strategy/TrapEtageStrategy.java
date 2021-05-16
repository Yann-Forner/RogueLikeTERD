package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

/**
 * L'etage piege est un étage ressemblant à un labyrinthe.
 * @author Quentin
 */
public class TrapEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage,this, RoomFactory.roomType.TRAP);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_PURPLE));
        Procedure.setRandomUP(etage);
        setItems(etage);
    }

    @Override
    public void EtageFusion(Etage etage, Cell.Style style_fusion) {
        ArrayList<Position> doors = new ArrayList<>();
        for(Room r : etage.getRooms()) {
            for (int i = 0; i < Procedure.getRandomInt(3,1); i++) {
                Position pos = Procedure.getRelativeRandomPosition(r);
                r.get(pos).updateCell(true, style_fusion);
                doors.add(pos.somme(r.getAbsolutePos()));
            }
        }
        for (int i = 0; i < doors.size()-1; i++) {
            for(Position p : Tools.Astar(etage,doors.get(i),doors.get(i+1), Tools.PathType.LABY)){
                etage.get(p).updateCell(true, style_fusion);
            }
        }
        cleanFusion(etage,style_fusion);
    }

    @Override
    public void setItems(Etage e) {
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            switch(i) {
                case 0 -> e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.STRENGTH_POTION));
                case 1 -> e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.INVUL_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            e.addItem(FoodFactory.getNewFood(e, FoodFactory.FoodType.CARROT));
        }

        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 20;
    }
}
