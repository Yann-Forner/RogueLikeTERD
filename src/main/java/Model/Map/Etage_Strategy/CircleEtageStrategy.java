package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Un étage rempli de salles rondes.
 * @author Yann
 */
public class CircleEtageStrategy extends EtageStrategy{

    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.CIRCLENORMAL);
        EtageFusion(etage, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE));
        setSpecialCell(etage, etageDepart);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public void setMonsters(Etage etage) {
        super.setMonsters(etage);
        for(int i = 0; i < Procedure.getRandomInt(3, 0); i++) {
            etage.addMonster(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.BEE));
        }
        for(int i = 0; i < Procedure.getRandomInt(3, 0); i++) {
            etage.addMonster(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.GHOST));
        }
    }

    @Override
    public void setItems(Etage e) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            switch(i) {
                case 0 -> e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.HEAL_POTION));
                case 1 -> e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.STRENGTH_POTION));
            }
        }
        for (int i = 0; i < Procedure.getRandomInt(5,0); i++) {
            e.addItem(FoodFactory.getNewFood(e, FoodFactory.FoodType.ORANGE));
        }

        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }

}
