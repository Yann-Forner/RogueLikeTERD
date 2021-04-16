package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.TriangleRoomStrategy;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class NormalTriangleStrategy extends TriangleRoomStrategy {

    @Override
    public void composeRoom(Room r) {
        this.fillTriangle(r, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW, "."));
        setStyleCell(r);
    }

    @Override
    protected void setStyleCell(Room r) {
        Cell.Style cactus = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW,	"\uD83C\uDF35", "C");

        for (int i = 0; i < Procedure.getRandomInt(6,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, cactus);
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,1);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 5;
    }
}
