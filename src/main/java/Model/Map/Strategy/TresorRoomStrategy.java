package Model.Map.Strategy;

import Model.Cell;
import Model.Map.Room;

import java.util.ArrayList;

public class TresorRoomStrategy implements RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        int x = (r.getWidth()-1)/2;
        int y = (r.getHeigth()-1)/2;
        r.set(x,y,new Cell( true,Cell.CellType.CHEST));
    }
}
