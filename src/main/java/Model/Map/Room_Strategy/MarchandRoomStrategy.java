package Model.Map.Room_Strategy;

import Model.Map.Etage;
import Model.Map.Room;

public class MarchandRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        //TODO rajouter le marchand quand il sera implémentés
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 0;
    }

}
