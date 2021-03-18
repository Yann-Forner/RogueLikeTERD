package Model.Map.Strategy;

import Model.Map.Cell;
import Model.Map.Room;

public class TresorRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        int x = (r.getWidth()-1)/2;
        int y = (r.getHeigth()-1)/2;
        r.set(x,y,new Cell( true,Cell.CellType.CHEST));
    }
}
