import Model.*;

import java.util.ArrayList;

public class Main {

    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;

    public static void main(String[] args) {
        Map myMap = new Map(MapWidth,MapHeigth);
        Room r=Procedure.getRandomRoom();
        Procedure.setRamdomRooms(myMap);
        myMap.addEntity(new BasicPlayer(new Position(11, 16), myMap));
        myMap.upgradeCellsWithEntitys();
        for(Room rm: myMap.getRooms()){
            System.out.println(rm.getDoors());
        }
        System.out.println(myMap.cheminFind(myMap.getRooms().get(0).getDoors().get(0),myMap.getRooms().get(1).getDoors().get(0)));
        myMap.setPath();
        new Affichage(myMap);
    }
}
