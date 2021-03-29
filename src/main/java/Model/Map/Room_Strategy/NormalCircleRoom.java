package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class NormalCircleRoom extends CircleRoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.BresenhamCircle(r);
        super.fillInteriorCircle(r);
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage, room, Procedure.getRandomInt(7,0));
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 5;
    }
}
