package Model.Map.Room_Strategy;

import Model.Map.Etage;
import Model.Map.Room;

public class NormalTriangleStrategy extends TriangleRoomStrategy{

    @Override
    public void composeRoom(Room r) {
        super.fillTriangle(r);
        System.out.println(r);
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,1);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 5;
    }
}
