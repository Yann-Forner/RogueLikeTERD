package Model.Map.Etage_Strategy;

import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;
import Model.Utils.Start;
import Model.Utils.TourManager;

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
        setItems(etage);
        TourManager.getExecutor().scheduleAtFixedRate(() -> {
            Player player = Start.getPlayer();
            if(Objects.requireNonNull(player).getEtage()==etage){
                player.updatePV((int)(((double) player.getPv())/10),true);
            }
        },20,300, TimeUnit.MILLISECONDS);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}
