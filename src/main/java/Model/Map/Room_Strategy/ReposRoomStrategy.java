package Model.Map.Room_Strategy;

import Model.Entitys.Items.ItemFactory;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Procedure;

public class ReposRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
    }

    @Override
    public void setMonsters(Room r) {

    }

    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addItem(ItemFactory.getNewItem(r, ItemFactory.ItemType.HEAL_POTION));
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
