package Model.Map.Room_Strategy;

import Model.Entitys.Items.Misc.Chest;
import Model.Entitys.Items.Misc.StackOfMoney;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

/**
 * Défini une salle de trésor
 * @author Quentin,Yann
 */
public class TresorRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
    }

    @Override
    public void setMonsters(Room r) {
        for (int i = 0; i < Procedure.getRandomInt(2,0); i++) {
            r.addMonster(MonsterFactory.getNewMonster(r, MonsterFactory.MonsterType.GHOST));
        }
    }

    @Override
    public void setItems(Room r) {
        int x = (r.getWidth()-1)/2;
        int y = (r.getHeigth()-1)/2;
        r.addItem(new Chest(r, new Position(x,y)));

        int rand = Procedure.getRandomInt(2, 0);
        for(int i = 0; i < rand; i++) {
            r.addItem(new StackOfMoney(r, Procedure.getAccesibleRandomPosition(true, r), 50));
        }
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public String toString() {
        return "TresorRoomStrategy";
    }

}
