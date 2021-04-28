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
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            switch(i) {
                case 0 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.HEAL_POTION));
                case 1 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.STRENGTH_POTION));
                case 2 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.INVUL_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            switch(i) {
                case 0 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.SWORD));
                case 1 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.BOW));
                case 2 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.KNIFE));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            switch(i) {
                case 0 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.APPLE));
                case 1 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.BANANA));
                case 2 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.CARROT));
                case 3 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.ORANGE));
            }
        }
    }

    @Override
    public String toString() {
        return "NormalRoomStrategy";
    }
}
