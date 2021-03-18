package Model.Map.Strategy;

import Model.Map.Cell;
import Model.Map.Room;

public abstract class RoomStrategy{

    public void composeRoom(Room r){
        r.fillMap(new Cell(true, Cell.CellType.NORMAL));
    }

}
