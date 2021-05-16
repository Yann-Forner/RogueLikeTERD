package Model.Map.Room_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;


/**
 * Création de la room du marchand
 *
 * @author Gillian
 */
public class MarchandRoomStrategy extends RoomStrategy{

    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
    }

    //TODO CONVERTIR LE STYLE EN VRAI SAC D'ARGENT

    @Override
    protected void setStyleCell(Room r) {
    }


    @Override
    public void setMonsters(Room r) {
        if(Marchand.getState() != Marchand.STATE.DEAD){
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.MARCHAND));
        }
    }


    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(5,0); i++) {
            switch(i) {
                case 0 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.HEAL_POTION));
                case 1 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.STRENGTH_POTION));
                case 2 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.INVUL_POTION));
                case 3 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.ENDURENCE_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            switch(i) {
                case 0 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.SWORD));
                case 1 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.BOW));
                case 2 -> r.addItem(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.WAND));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(5,0); i++) {
            switch(i) {
                case 0 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.APPLE));
                case 1 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.BURGER));
                case 2 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.BANANA));
                case 3 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.CARROT));
                case 4 -> r.addItem(FoodFactory.getNewFood(r, FoodFactory.FoodType.ORANGE));
            }
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public String toString() {
        return "MarchandRoomStrategy";
    }

}
