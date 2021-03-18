package Model.Map.Strategy;


import Model.Cell;
import Model.Map.Room;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        r.fillMap(new Cell(true, Cell.CellType.NORMAL));
    }
}
