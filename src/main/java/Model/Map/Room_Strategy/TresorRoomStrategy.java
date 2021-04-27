package Model.Map.Room_Strategy;

import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Entitys.Player.Classes.Archer;
import Model.Entitys.Player.Classes.Warrior;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Procedure;
import Model.Utils.Start;

public class TresorRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        int x = (r.getWidth()-1)/2;
        int y = (r.getHeigth()-1)/2;
        r.set(x,y,new Cell( true,new Cell.Style(Cell.Style.CellType.CHEST)));
    }

    @Override
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.GHOST));
        }
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
        return super.noCollision(etage,room,0);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 0;
    }

    @Override
    public String toString() {
        return "TresorRoomStrategy";
    }

}
