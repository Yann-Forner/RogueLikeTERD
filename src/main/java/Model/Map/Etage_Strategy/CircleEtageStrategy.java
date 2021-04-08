package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

public class CircleEtageStrategy extends  EtageStrategy{

    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.CIRCLENORMAL);
        EtageFusion(etage, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE));
        setSpecialCell(etage);
        //setMobs(etage);
    }

    @Override
    protected void EtageFusion(Etage etage, Cell.Style style_fusion) {
        super.EtageFusion(etage, style_fusion);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }

    @Override
    public void setSpecialCell(Etage etage) {
        super.setSpecialCell(etage);
    }

    @Override
    public void setMobs(Etage etage) {
        super.setMobs(etage);
    }
}
