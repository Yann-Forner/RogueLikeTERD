package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class MarchandRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        //TODO rajouter le marchand quand il sera implémentés
        Position p1 = Procedure.getAccesibleRandomPosition(false, r);
        r.get(p1).updateCell(true, Cell.CellType.UP);
        Position p2 = Procedure.getAccesibleRandomPosition(false, r);
        r.get(p2).updateCell(true, Cell.CellType.DOWN);
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
