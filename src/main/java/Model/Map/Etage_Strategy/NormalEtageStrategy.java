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
        RoomFusion(etage);
        Procedure.setRandomChest(etage,3);
        Procedure.setRandomUPnDOWN(etage);
        Position accesibleRandomPosition = Procedure.getAccesibleRandomPosition(false, etage);
        etage.get(accesibleRandomPosition).updateCell(true, Cell.CellType.TRAP_ROOM);
        Procedure.setRandomMob(etage);
    }

    @Override
    public void RoomFusion(Etage etage) {
        super.RoomFusion(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }

}
