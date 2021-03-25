package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

public abstract class RoomStrategy{

    public void composeRoom(Room r){
        r.fillMap(new Cell(true, Cell.CellType.NORMAL));
    }

    protected boolean noCollision(Etage etage,Room room,int DistanceMin){
        for (int y = 0; y < room.getHeigth()+DistanceMin*2; y++) {
            for (int x = 0; x < room.getWidth()+DistanceMin*2; x++) {
                if (etage.get(Math.max(Math.min(room.getAbsolutePos().getX() + x - DistanceMin, etage.getWidth()-1), 0), Math.max(Math.min(room.getAbsolutePos().getY() + y - DistanceMin, etage.getHeigth()-1),0)).getType() != Cell.CellType.VOID){
                    return false;
                }
            }
        }
        return true;
    }

    public abstract boolean noCollision(Etage etage,Room room);
    public abstract int getNbrMaxMobPerRoom();
}