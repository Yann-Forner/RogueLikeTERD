package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

public class NormalEtageStrategy extends EtageStrategy{


    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.NORMAL);
        EtageFusion(etage);
        setSpecialCell(etage);
        //setMobs(etage);
    }

    @Override
    public void EtageFusion(Etage etage) {
        super.EtageFusion(etage);
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
