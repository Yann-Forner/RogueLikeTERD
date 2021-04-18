package Model.Map.Room_Strategy;

import Model.Entitys.Items.ItemFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.CircleRoomStrategy;
import Model.Utils.Procedure;

public class MiniBossRoomStrategy extends CircleRoomStrategy {
    @Override
    public void composeRoom(Room r) {
        super.BresenhamCircle(r);
        super.fillInteriorCircle(r);
    }

    @Override
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.BEE));
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.ALIEN));
        }
    }

    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addItem(ItemFactory.getNewItem(r, ItemFactory.ItemType.HEAL_POTION));
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,4);
    }
}
