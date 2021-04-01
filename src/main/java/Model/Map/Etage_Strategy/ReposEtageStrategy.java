package Model.Map.Etage_Strategy;

import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Procedure;

public class ReposEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.REPOS);
        EtageFusion(etage);
        Procedure.setRandomUPnDOWN(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
