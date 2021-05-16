package Model.Map.Room_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Etage;
import Model.Map.Cell;
import Model.Map.Room;
import Model.Utils.Procedure;

/**
 * Défini une salle normale (rectangulaire)
 * @auhtor Quentin,Yann
 */
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
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.ZOMBIE));
        }
    }

    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            switch(i) {
                case 0 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.HEAL_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.APPLE));
        }
    }

    @Override
    public String toString() {
        return "NormalRoomStrategy";
    }
}
