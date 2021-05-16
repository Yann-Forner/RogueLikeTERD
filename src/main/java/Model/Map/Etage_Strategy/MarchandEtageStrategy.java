package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Potions.PotionFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;


/**
 * Création de l'étage du marchand, cet étage comporte une seule salle avec un marchand.
 * @author Gillian
 */
public class MarchandEtageStrategy extends EtageStrategy{


    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.MARCHAND);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW));
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public void setItems(Etage e) {
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.ENDURENCE_POTION));
        }

        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
