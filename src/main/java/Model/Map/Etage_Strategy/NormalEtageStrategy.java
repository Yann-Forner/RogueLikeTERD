package Model.Map.Etage_Strategy;

import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Cet étage est composé de plusieurs salles, elle seront toutes de forme normale (rectangulaire)
 * @author Yann
 */
public class NormalEtageStrategy extends EtageStrategy{

    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.NORMAL);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL));
        setSpecialCell(etage);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
