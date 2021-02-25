

public class Main {
    public static void main(String[] args) {
        Map myMap = new Map(40,40);
        myMap.addRoom(new Room(),new Position(10,15));
        new Affichage(myMap);
        System.out.println(new Room());
    }
}
