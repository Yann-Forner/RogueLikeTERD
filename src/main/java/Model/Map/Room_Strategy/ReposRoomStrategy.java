package Model.Map.Room_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Défini une salle de repos.
 * @author Quentin,Yann
 */
public class ReposRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_PURPLE, ".")));
        setStyleCell(r);
    }

    @Override
    protected void setStyleCell(Room r) {
        Cell.Style vase = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_GREY,"\uD83C\uDFFA", "v");
        Cell.Style fleure_type1 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83C\uDF3B", "a");
        Cell.Style fleure_type2 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_BLACK,"\uD83C\uDF3C", "b");
        Cell.Style fleure_type3 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83C\uDF37", "c");
        Cell.Style fleure_type4 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_BLACK,"\uD83C\uDF31", "d");
        Cell.Style fleure_type5 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,"\uD83C\uDF3A", "e");
        Cell.Style fleure_type6 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_BLACK,"\uD83E\uDD40", "f");
        Cell.Style fleure_type7 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83C\uDF3F", "g");
        Cell.Style fleure_type8 = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_GREY,"\uD83C\uDF44", "h");
        for (int i = 0; i < Procedure.getRandomInt(3,1); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, vase);
        }
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type1);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,1); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type2);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type3);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type4);
        }
        for (int i = 0; i < Procedure.getRandomInt(4,2); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type5);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type6);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,1); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type7);
        }
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, fleure_type8);
        }
    }

    @Override
    public void setMonsters(Room r) {
        //Aucun monstre
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
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public String toString() {
        return "ReposRoomStrategy";
    }
}
