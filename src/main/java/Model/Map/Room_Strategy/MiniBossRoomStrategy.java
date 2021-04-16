package Model.Map.Room_Strategy;

import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.CircleRoomStrategy;

public class MiniBossRoomStrategy extends CircleRoomStrategy {
    @Override
    public void composeRoom(Room r) {
        super.BresenhamCircle(r);
        super.fillInteriorCircle(r);
    }

    @Override
    public void setRoomMonsters(Etage e) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,4);
    }
}
