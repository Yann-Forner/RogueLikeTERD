package Model.Map.Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

public class TresorRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        int x = (r.getWidth()-1)/2;
        int y = (r.getHeigth()-1)/2;
        r.set(x,y,new Cell( true,Cell.CellType.CHEST));
    }

    @Override
    public boolean isCollision(Etage etage, Room room, Position pos) {
        return super.isCollision(etage,room,pos,0);
    }

}
