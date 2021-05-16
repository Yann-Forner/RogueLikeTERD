package Model.Map.Room_Strategy;

import Model.Entitys.Items.Potions.PotionFactory;
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
        //TODO à déplacer dans items
        Cell.Style car = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83D\uDE97", "D");
        Cell.Style trophy = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83C\uDFC6", "D");

        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, car);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, trophy);
        }
    }


    @Override
    public void setMonsters(Room r) {
        if(Marchand.getState() != Marchand.STATE.DEAD){
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.MARCHAND));
        }
    }


    @Override
    public void setItems(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            r.addItem(PotionFactory.getNewPotion(r, PotionFactory.PotionType.ENDURENCE_POTION));
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
