package Model.Map.Strategy;

import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

public class LabyRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room, Position pos) {
        return super.noCollision(etage,room,pos,0);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 5;
    }

    @Override
    public int getNbrMaxRoom() {
        return 10;
    }

}
