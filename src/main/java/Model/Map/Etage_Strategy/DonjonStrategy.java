package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.util.ArrayList;
import java.util.Random;

public class DonjonStrategy extends EtageStrategy {

    private final ArrayList<RoomFactory.roomType> myRooms = new ArrayList<>() {{
        add(RoomFactory.roomType.MINIBOSS);
        add(RoomFactory.roomType.NORMAL);
        add(RoomFactory.roomType.NORMAL);
        add(RoomFactory.roomType.MARCHAND);
        add(RoomFactory.roomType.TRESOR);
        add(RoomFactory.roomType.CIRCLENORMAL);
    }};

    @Override
    public void composeEtage(Etage etage) {
        ArrayList<RoomFactory.roomType> roomsOfEtage = new ArrayList<>();
        for (int i = 0; i < getNbrMaxRoom() ; i++) {
            roomsOfEtage.add(myRooms.get(new Random().nextInt((myRooms.size()))));
        }
        Procedure.setRandomRooms(etage,this,roomsOfEtage);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,";"));
        setSpecialCell(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
