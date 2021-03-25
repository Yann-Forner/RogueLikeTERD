package Model.Map.Room_Strategy;

import Model.Map.Etage;
import Model.Map.Room;

public class BigBossRoomStrategy extends BossRoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.BresenhamCircle(r);
        super.fillInteriorCircle(r);

    }
    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }
}
