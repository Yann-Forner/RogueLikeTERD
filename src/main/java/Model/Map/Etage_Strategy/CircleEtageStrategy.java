package Model.Map.Etage_Strategy;

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
        for(int i = 0; i < Procedure.getRandomInt(3,0); i++){
            etage.addMonster(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.BEE));
        }
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }

}
