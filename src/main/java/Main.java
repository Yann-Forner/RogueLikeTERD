import Model.*;

import java.util.ArrayList;

public class Main {

    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;

    public static void main(String[] args) {
        Map myMap = new Map(MapWidth,MapHeigth);
        //Model.Procedure.setRamdomRooms(myMap);
        //myMap.addEntity(new BasicPlayer(new Position(11, 16), myMap));
        //myMap.upgradeCellsWithEntitys();
        //System.out.println(myMap.cheminFind(myMap.getRooms().get(0).getDoors().get(0),myMap.getRooms().get(1).getDoors().get(0)));
        //myMap.setPath();
        Procedure.setRandomRooms(myMap);
        Model.Procedure.setRandomMob(myMap);

        //myMap.ligne(new Position(10,5),new Position(30,30));
        //myMap.ligneV2(new Position(10,5),new Position(30,30));

        new Affichage(myMap);
        myMap.RoomFusion();
        new Affichage(myMap);
    }
}
