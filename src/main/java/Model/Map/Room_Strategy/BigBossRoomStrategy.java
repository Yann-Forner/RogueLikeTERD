package Model.Map.Room_Strategy;

import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.CircleRoomStrategy;
import Model.Utils.Procedure;

public class BigBossRoomStrategy extends CircleRoomStrategy {
    @Override
    public void composeRoom(Room r) {
        super.BresenhamCircle(r);
        super.fillInteriorCircle(r);

    }

    @Override
    public void setMonsters(Room r) {
        switch (Procedure.getRandomInt(2,0)){
            case 0 -> r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.SNAKE));
            case 1 -> r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.BIGMONSTER));
            case 2 -> r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.INVOQUEUR));
        }
    }

    @Override
    public void setItems(Room r) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }
}
