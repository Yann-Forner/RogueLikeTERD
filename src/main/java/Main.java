import Model.*;

import java.util.ArrayList;
import java.util.Random;

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

        Affichage.map(myMap);
        myMap.RoomFusion();
        Affichage.map(myMap);
        //Affichage.Rooms(myMap);

        Position pos1=Procedure.getAccesibleRandomPosition(myMap);
        Position pos2=Procedure.getAccesibleRandomPosition(myMap);

        ArrayList<Position> astar = myMap.Astar(pos1,pos2);
        Affichage.Path(myMap,astar);
    }
}
