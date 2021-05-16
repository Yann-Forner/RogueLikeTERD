package Model.Map.Room_Strategy;

import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.util.Objects;

/**
 * Défini une salle piégée
 * @author Quentin
 */
public class TrapRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(false, new Cell.Style(Cell.Style.CellType.BORDER,Affichage.RED)));
    }

    @Override
    public void setMonsters(Room r) {

    }

    @Override
    public void setItems(Room r) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,1);
    }

    @Override
    public String toString() {
        return "TrapRoomStrategy";
    }

}
