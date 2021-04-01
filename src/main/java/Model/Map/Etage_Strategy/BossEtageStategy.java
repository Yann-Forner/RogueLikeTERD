package Model.Map.Etage_Strategy;

import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Procedure;

public class BossEtageStategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.BOSS);
        EtageFusion(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
