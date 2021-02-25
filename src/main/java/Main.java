import Model.*;

public class Main {
    public static void main(String[] args) {
        int MapWidth = 40;
        int MapHeigth = 40;
        Map myMap = new Map(MapWidth,MapHeigth);
        Room r=Procedure.getRandomRoom();
        Procedure.getRamdomRooms(myMap);
        new Affichage(myMap);
    }
}
