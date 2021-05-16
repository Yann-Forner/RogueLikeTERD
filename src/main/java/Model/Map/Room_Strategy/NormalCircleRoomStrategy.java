package Model.Map.Room_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.Room_Strategy.Formes.CircleRoomStrategy;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Défini une salle ronde normale
 * @auhtor Yann,Quentin
 */
public class NormalCircleRoomStrategy extends CircleRoomStrategy {
    @Override
    public void composeRoom(Room r) {
        BresenhamCircle(r);
        fillInteriorCircle(r);
        setStyleCell(r);
    }

    @Override
    protected void setStyleCell(Room r) {
        Cell.Style coquillage = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,"\uD83D\uDC1A", "T");
        Cell.Style vagues = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_GREY,"\uD83C\uDF0A", "Ø");

        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, coquillage);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, vagues);
        }
    }

    @Override
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.BIRD));
        }
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.VOLCANO));
        }
    }

    @Override
    public void setItems(Room r) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage, room, Procedure.getRandomInt(7,0));
    }

    @Override
    public String toString() {
        return "NormalCircleRoomStrategy";
    }
}
