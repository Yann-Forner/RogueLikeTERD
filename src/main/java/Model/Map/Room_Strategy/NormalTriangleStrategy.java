package Model.Map.Room_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.TriangleRoomStrategy;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Défini une salle normale triangulaire
 * @auhtor Quentin,Yann
 */
public class NormalTriangleStrategy extends TriangleRoomStrategy {

    @Override
    public void composeRoom(Room r) {
        fillTriangle(r, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW, "."));
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
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,1); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.SKULL));
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
        for (int i = 0; i < Procedure.getRandomInt(5,0); i++) {
            switch(i) {
                case 0 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.APPLE));
                case 1 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.SODA));
                case 2 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.BANANA));
                case 3 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.CARROT));
                case 4 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.ORANGE));
            }
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,1);
    }

    @Override
    public String toString() {
        return "NormalTriangleStrategy";
    }
}
