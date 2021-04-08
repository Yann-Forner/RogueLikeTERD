package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Position;

public abstract class TriangleRoomStrategy extends  RoomStrategy{

    public void fillTriangle(Room r){

        Position posAngle1 = new Position((r.getWidth()-1)/2, 1);
        boolean stop = false;
        for (int i = 0; i <  r.getHeigth()-2; i++) {
            if(stop)break;
                r.set(posAngle1.getX(), posAngle1.getY() + i, new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL)));
                for (int j = 1; j < i + 1; j++) {
                    try {
                        r.set(posAngle1.getX() + j, posAngle1.getY() + i, new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL)));
                        r.set(posAngle1.getX() - j, posAngle1.getY() + i, new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL)));
                    } catch (IndexOutOfBoundsException e) {
                        stop = true;
                        break;
                    }

                }

        }

    }
}
