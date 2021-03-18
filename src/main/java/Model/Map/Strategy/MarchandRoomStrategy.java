package Model.Map.Strategy;

import Model.Cell;
import Model.Map.Room;
import Model.Position;
import Model.Procedure;

import javax.swing.*;

public class MarchandRoomStrategy implements RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        //TODO rajouter le marchand quand il sera implémentés
        Position p1 = Procedure.getAccesibleRandomPosition(false, r);
        r.get(p1).updateCell(true, Cell.CellType.UP);
        Position p2 = Procedure.getAccesibleRandomPosition(false, r);
        r.get(p2).updateCell(true, Cell.CellType.DOWN);
    }
}
