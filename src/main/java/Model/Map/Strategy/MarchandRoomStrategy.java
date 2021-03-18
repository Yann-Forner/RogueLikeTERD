package Model.Map.Strategy;

import Model.Cell;
import Model.Map.Room;

public class MarchandRoomStrategy implements RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        //TODO rajouter le marchand quand il sera implémentés
        r.set(0,0,new Cell(true,Cell.CellType.UP));
        r.set(4,0,new Cell(true,Cell.CellType.DOWN));
    }
}
