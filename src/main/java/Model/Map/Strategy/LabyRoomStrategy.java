package Model.Map.Strategy;

import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

public class LabyRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {

    }

    @Override
    public boolean isCollision(Etage etage, Room room, Position pos) {
        return super.isCollision(etage,room,pos,0);
    }

}
