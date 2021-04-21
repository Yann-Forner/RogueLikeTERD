package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.io.Serializable;

public abstract class RoomStrategy implements Serializable {

    public void composeRoom(Room r){
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_YELLOW, ".")));
        setStyleCell(r);
    }

    protected void setStyleCell(Room r){
        Cell.Style palmier = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,"\uD83C\uDF34", "T");
        Cell.Style barierre = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83D\uDEA7", "Ø");
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, palmier);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, barierre);
        }
    }

    protected boolean noCollision(Etage etage,Room room,int DistanceMin){
        for (int y = 0; y < room.getHeigth()+DistanceMin*2; y++) {
            for (int x = 0; x < room.getWidth()+DistanceMin*2; x++) {
                if (etage.get(Math.max(Math.min(room.getAbsolutePos().getX() + x - DistanceMin, etage.getWidth()-1), 0), Math.max(Math.min(room.getAbsolutePos().getY() + y - DistanceMin, etage.getHeigth()-1),0)).getType() != Cell.Style.CellType.VOID){
                    return false;
                }
            }
        }
        return true;
    }

    public abstract void setMonsters(Room r);
    public abstract void setItems(Room r);
    public abstract boolean noCollision(Etage etage,Room room);
    public abstract int getNbrMaxMobPerRoom();
}
