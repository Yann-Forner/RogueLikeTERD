package Model.Map.Room_Strategy;


import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        //super.composeRoom(r);
        Cell.CellType type = Cell.CellType.NORMAL;
        type.setString(Affichage.GREEN+'.');
        r.fillMap(new Cell(true, type));
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
