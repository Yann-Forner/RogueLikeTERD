import Model.*;

public class Main {
    public static void main(String[] args) {
        int MapWidth = 40;
        int MapHeigth = 40;
        Map myMap = new Map(MapWidth,MapHeigth);
        Room r=Procedure.getRandomRoom();
        Procedure.getRamdomRooms(myMap);
        myMap.addEntity(new BasicPlayer(new Position(11, 16), myMap));
        new Affichage(myMap);
    }
}
