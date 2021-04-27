package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

/**
 * Cet étage est composé d'une seule salle grande qui comporte un boss
 * @auhtor Yann
 */

public class BossEtageStategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.BOSS);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY));
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
