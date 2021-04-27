package Model.Map.Room_Strategy;

import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;


/**
 * Création de la room du marchand
 *
 * @author Gillian
 */
public class MarchandRoomStrategy extends RoomStrategy{


    /**
     * Création de la room
     * @param r
     * @author Gillian
     */
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
    }

    /**
     * Permet de définir le style de la cellule
     * @param r
     * @author Gillian
     */

    //TODO CONVERTIR LE STYLE EN VRAI SAC D'ARGENT

    @Override
    protected void setStyleCell(Room r) {
        //TODO à déplacer dans items
        Cell.Style dollar = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83D\uDCB5", "D");
        Cell.Style dollarAiles = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN,	"\uD83D\uDCB8", "D");
        Cell.Style yen = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW,"\uD83D\uDCB4", "Ø");

        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, dollar);
        }
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, dollarAiles);
        }
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, yen);
        }
    }


    /**
     * Ajout du marchand dans la salle
     * @param r
     * @author Gillian
     */
    @Override
    public void setMonsters(Room r) {
        r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.MARCHAND));
    }

    /**
     * Ajoute les stacks d'argent dans la salle
     * @param r
     * @author Gillian
     */
    @Override
    public void setItems(Room r) {

    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 0;
    }

    @Override
    public String toString() {
        return "MarchandRoomStrategy";
    }

}
