package Model.Map.Room_Strategy.Formes;

import Model.Map.Cell;
import Model.Map.Room;
import Model.Map.Room_Strategy.RoomStrategy;
import Model.Utils.Affichage;
import Model.Utils.Position;

public abstract class TriangleRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        fillTriangle(r);
    }

    public void fillTriangle(Room r){
        fillTriangle(r, new Cell.Style(Cell.Style.CellType.NORMAL));
    }

    public void fillTriangle(Room r, Cell.Style s){

        Position posAngle1 = new Position((r.getWidth()-1)/2, 1);
        boolean stop = false;
        for (int i = 0; i <  r.getHeigth()-2; i++) {
            if(stop)break;
            r.set(posAngle1.getX(), posAngle1.getY() + i, new Cell(true, s));
            for (int j = 1; j < i + 1; j++) {
                try {
                    r.set(posAngle1.getX() + j, posAngle1.getY() + i, new Cell(true, s));
                    r.set(posAngle1.getX() - j, posAngle1.getY() + i, new Cell(true, s));
                } catch (IndexOutOfBoundsException e) {
                    //TODO c'est degeulasse de stopper avec une exception #YANN
                    stop = true;
                    break;
                }

            }

        }
    }
}
