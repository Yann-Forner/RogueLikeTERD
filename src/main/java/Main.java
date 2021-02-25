import Model.BasicPlayer;
import Model.Map;
import Model.Position;
import Model.Room;

public class Main {
    public static void main(String[] args) {
        Map myMap = new Map(40,40);
        myMap.addRoom(new Room(4, 10),new Position(10,15));
        myMap.addEntity(new BasicPlayer(new Position(11, 16), myMap));
        System.out.println(myMap);
    }
}
