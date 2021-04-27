package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;


/**
 * Création de l'étage du marchant
 * @author Gillian
 */
public class MarchandEtageStrategy extends EtageStrategy{


    /**
     * Composition de l'étage
     * @param etage
     * @author Gillian
     */
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.MARCHAND);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW));
        setMonsters(etage);
        setItems(etage);
    }

    /**
     * nombre de salle maximale : 1 pour le marchand.
     * @return 1
     * @author Gillian
     */
    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
