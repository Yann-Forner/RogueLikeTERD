package Model.Map.Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

public abstract class RoomStrategy{

    public void composeRoom(Room r){
        r.fillMap(new Cell(true, Cell.CellType.NORMAL));
    }

    protected boolean isCollision(Etage etage,Room room,Position pos,int DistanceMin){
        for (int y = 0; y < room.getHeigth()+DistanceMin*2; y++) {
            for (int x = 0; x < room.getWidth()+DistanceMin*2; x++) {
                if (etage.get(Math.max(Math.min(pos.getX() + x - DistanceMin, etage.getWidth()-1), 0), Math.max(Math.min(pos.getY() + y - DistanceMin, etage.getHeigth()-1),0)).getType() != Cell.CellType.VOID){
                    return true;
                }
            }
        }
        return false;
    }

    public abstract boolean isCollision(Etage etage,Room room,Position pos);

    public int getNbrMaxRoom() {
        return 8;
    }
}
