package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class MarchandRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        //TODO rajouter le marchand quand il sera implémentés
    }

    @Override
    protected void setStyleCell(Room r) {
        Cell.Style dollar = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83D\uDCB5", "D");
        Cell.Style dollarAiles = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83D\uDCB8", "D");
        Cell.Style yen = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW,"\uD83D\uDCB4", "Ø");

        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, dollar);
        }
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, dollarAiles);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, yen);
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 0;
    }

}
