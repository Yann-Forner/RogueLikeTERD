package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class NormalEtageStrategy extends EtageStrategy{


    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.NORMAL);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL));
        setSpecialCell(etage);
        //TODO mobs
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
