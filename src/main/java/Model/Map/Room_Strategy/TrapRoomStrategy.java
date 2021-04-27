package Model.Map.Room_Strategy;

import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Classes.Archer;
import Model.Entitys.Player.Classes.Warrior;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Start;

public class TrapRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(false, new Cell.Style(Cell.Style.CellType.BORDER,Affichage.RED)));
        for (int i = 0; i < Procedure.getRandomInt(4,1); i++) {
            Position pos = Procedure.getRandomWall(r);
            //TODO empecher de tomber sur le même mur deux fois
            r.get(pos).updateCell(true, new Cell.Style(Cell.Style.CellType.NORMAL));
        }
    }

    @Override
    public void setMonsters(Room r) {

    }

    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            switch(i) {
                case 0 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.ItemType.HEAL_POTION));
                case 1 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.ItemType.STRENGTH_POTION));
                case 2 -> r.addItem(PotionFactory.getNewPotion(r, PotionFactory.ItemType.INVUL_POTION));
            }
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,1);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 1;
    }

    @Override
    public String toString() {
        return "TrapRoomStrategy";
    }

}
