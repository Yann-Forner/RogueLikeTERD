package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class TrapRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        Cell.CellType type = Cell.CellType.BORDER;
        type.setString(Affichage.RED+'*');
        r.fillMap(new Cell(false, type));
        for (int i = 0; i < Procedure.getRandomInt(4,1); i++) {
            Position pos = Procedure.getRandomWall(r);
            //TODO empecher le tomber sur le même mur deux fois
            r.get(pos).updateCell(true, Cell.CellType.NORMAL);
        }
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
