package Model.Map.Strategy;

import Model.Cell;
import Model.Map.Room;

import java.util.ArrayList;

public abstract class RoomStrategy{

    public void composeRoom(Room r){
        r.fillMap(new Cell(true, Cell.CellType.NORMAL));
    }

}
