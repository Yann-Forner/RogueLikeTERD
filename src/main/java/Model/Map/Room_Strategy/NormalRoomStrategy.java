package Model.Map.Room_Strategy;


import Model.Entitys.Monsters.MonsterFactory;
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
    public void setRoomMonsters(Etage e) {
        e.addMonster(MonsterFactory.getNewMonster(e, MonsterFactory.MonsterType.ZOMBIE));
        e.addMonster(MonsterFactory.getNewMonster(e, MonsterFactory.MonsterType.SKULL));
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
