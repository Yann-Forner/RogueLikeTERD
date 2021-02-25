public class Affichage {

    public Affichage(Map map){
        System.out.println(map);
        for (Room r: map.getRooms()) {
            System.out.println(r);
        }
    }
}
