package Model.Map.Etage_Strategy;

import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Procedure;

import java.util.ArrayList;
import java.util.Random;

public class DonjonStrategy extends EtageStrategy {

    private ArrayList<RoomFactory.roomType> myRooms = new ArrayList() {{
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
        System.out.println(myRooms);
        Procedure.setRandomRooms(etage,this,roomsOfEtage);
        EtageFusion(etage);
        setSpecialCell(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
