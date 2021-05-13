package Model.Map.Room_Strategy;

import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;

/**
 * Défini une salle de repos.
 * @author Quentin,Yann
 */
public class ReposRoomStrategy extends RoomStrategy {

    @Override
    public void composeRoom(Room r) {
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.PURPLE,"\u2299")));
        setStyleCell(r);
    }

    @Override
    protected void setStyleCell(Room r) {
        r.get(0,r.getHeigth()/2).updateCell(true,new Cell.Style(Cell.Style.CellType.UP));
        r.get(r.getWidth()-1,r.getHeigth()/2).updateCell(true,new Cell.Style(Cell.Style.CellType.DOWN));
        r.get(r.getWidth()/2,r.getHeigth()/2).setEntity(WeaponFactory.getNewWeapon(r, WeaponFactory.WeaponType.SWORD));
    }

    @Override
    public void setMonsters(Room r) {
        //Aucun monstre
    }

    @Override
    public void setItems(Room r) {
        //TODO generer un coffre (fort) au centre de la room #JP
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
