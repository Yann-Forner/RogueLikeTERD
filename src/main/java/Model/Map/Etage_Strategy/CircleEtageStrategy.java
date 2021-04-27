package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Un étage rempli de salles rondes
 * @author Yann
 */
public class CircleEtageStrategy extends  EtageStrategy{

    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.CIRCLENORMAL);
        EtageFusion(etage, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE));
        setSpecialCell(etage);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }

}
