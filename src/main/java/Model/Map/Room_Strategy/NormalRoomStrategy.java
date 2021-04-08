package Model.Map.Room_Strategy;


import Model.Map.Etage;
import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL)));
        r.get(Procedure.getAccesibleRandomPosition(false,r)).updateCell(true,new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.GREEN,"\uD83C\uDF34"));
        r.get(Procedure.getAccesibleRandomPosition(false,r)).updateCell(true,new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.GREEN,"\uD83C\uDF34"));
        r.get(Procedure.getAccesibleRandomPosition(false,r)).updateCell(true,new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.GREEN,"\uD83C\uDF34"));
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
