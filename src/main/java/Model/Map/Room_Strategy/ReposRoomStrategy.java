package Model.Map.Room_Strategy;

import Model.Entitys.Items.Potions.PotionFactory;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Procedure;

/**
 * Défini une salle de repos
 * @auhtor Quentin,Yann
 */
public class ReposRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
    }

    @Override
    public void setMonsters(Room r) { }

    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            switch(i) {
                case 0 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.HEAL_POTION));
                case 1 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.STRENGTH_POTION));
                case 2 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.INVUL_POTION));
            }
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public String toString() {
        return "ReposRoomStrategy";
    }
}
