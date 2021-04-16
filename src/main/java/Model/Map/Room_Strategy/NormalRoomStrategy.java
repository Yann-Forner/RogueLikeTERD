package Model.Map.Room_Strategy;


import Model.Entitys.Monsters.MonsterFactory;
import Model.Entitys.Monsters.Zombie;
import Model.Map.Etage;
import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Procedure;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL)));
        setStyleCell(r);
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage, room, Procedure.getRandomInt(7,0));
    }

    @Override
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.ZOMBIE));
        }
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 5;
    }
}
