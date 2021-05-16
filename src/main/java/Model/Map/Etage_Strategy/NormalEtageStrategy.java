package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.Procedure;

import java.util.Objects;

/**
 * Cet étage est composé de plusieurs salles, elle seront toutes de forme normale (rectangulaire)
 * @author Yann
 */
public class NormalEtageStrategy extends EtageStrategy{

    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.NORMAL);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL));
        setSpecialCell(etage, etageDepart);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public void setMonsters(Etage etage) {
        super.setMonsters(etage);
        for (int i = 0; i < Procedure.getRandomInt(5,0); i++) {
            etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.GHOST)));
        }
    }

    @Override
    public void setItems(Etage e) {
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.HEAL_POTION));
            switch(i) {
                case 2,3 -> e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.STRENGTH_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            e.addItem(FoodFactory.getNewFood(e, FoodFactory.FoodType.APPLE));
        }

        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
