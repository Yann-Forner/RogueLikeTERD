package Model.Map.Etage_Strategy;

import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Cet étage est composé d'une seule salle qui permet de se reposer.
 * @author Yann
 */
public class ReposEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.REPOS);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,";"));
        setSpecialCell(etage,false);
        setItems(etage);
        TourManager.getExecutor().scheduleAtFixedRate(() -> {
            Player player = Start.getPlayer();
            if(Objects.requireNonNull(player).getEtage()==etage){
                player.updatePV((int)(((double) player.getPv())/10),true);
            }
        },20,300, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setSpecialCell(Etage etage, boolean etageDepart) {
        Room r = etage.getRooms().get(0);
        Position posUp = new Position(0,r.getHeigth()/2).somme(r.getAbsolutePos());
        etage.setUp(posUp);
        etage.get(posUp).updateCell(true,new Cell.Style(Cell.Style.CellType.UP));
        Position posDown = new Position(r.getWidth()-1,r.getHeigth()/2).somme(r.getAbsolutePos());
        etage.setDown(posDown);
        etage.get(posDown).updateCell(true,new Cell.Style(Cell.Style.CellType.DOWN));
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
