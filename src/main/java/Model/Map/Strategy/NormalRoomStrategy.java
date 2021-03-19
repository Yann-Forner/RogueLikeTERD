package Model.Map.Strategy;


import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Position;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        Cell.CellType type = Cell.CellType.NORMAL;
        type.setString(Affichage.GREEN+'.');
        r.fillMap(new Cell(true, type));
    }

    @Override
    public boolean isCollision(Etage etage, Room room, Position pos) {
        return super.isCollision(etage, room, pos,0);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
