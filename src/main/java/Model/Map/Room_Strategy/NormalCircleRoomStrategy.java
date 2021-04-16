package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.CircleRoomStrategy;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class NormalCircleRoomStrategy extends CircleRoomStrategy {
    @Override
    public void composeRoom(Room r) {
        BresenhamCircle(r);
        fillInteriorCircle(r);
        setStyleCell(r);
    }

    @Override
    protected void setStyleCell(Room r) {
        Cell.Style coquillage = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE,"\uD83D\uDC1A", "T");
        Cell.Style vagues = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83C\uDF0A", "Ø");

        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, coquillage);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, vagues);
        }
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