package Model.Map.Room_Strategy;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

/**
 * Défini une salle de repos.
 * @author Quentin,Yann
 */
public class ReposRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.PURPLE,"\u2299")));
    }

    @Override
    public void setMonsters(Room r) {
        //Aucun monstre
    }

    @Override
    public void setItems(Room r) {
        AbstractItem item = WeaponFactory.getNewWeapon(r,
                switch (Procedure.getRandomInt(2,0)){
                    case 0 -> WeaponFactory.WeaponType.WAND;
                    case 1 -> WeaponFactory.WeaponType.SWORD;
                    default -> WeaponFactory.WeaponType.BOW;
                });
        item.setPosition(new Position(r.getWidth()/2,r.getHeigth()/2));
        r.addItem(item);
    }

    @Override
    public boolean noCollision(Etage etage, Room room) {
        return super.noCollision(etage,room,0);
    }

    @Override
    public String toString() {
        return "ReposRoomStrategy";
    }
}
