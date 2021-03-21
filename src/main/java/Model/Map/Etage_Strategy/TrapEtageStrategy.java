package Model.Map.Etage_Strategy;

import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Procedure;

public class TrapEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage,this, RoomFactory.roomType.TRAP);
        RoomFusion(etage);
        Procedure.setRandomUP(etage);
        Procedure.setRandomMob(etage);
    }

    @Override
    public void RoomFusion(Etage etage) {
        super.RoomFusion(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 3;
    }
}
