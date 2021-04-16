package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class ReposEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.REPOS);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,";"));
        Procedure.setRandomUPnDOWN(etage);
        setMonsters(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
