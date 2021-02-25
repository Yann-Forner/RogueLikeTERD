import Exceptions.CollisionRoom;
import Model.*;
import java.util.Random;

public class Procedure {
    private final static Random rand =new Random();

    public static Room getRandomRoom(){
        int SIZEX = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        int SIZEY = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        Room r = new Room(SIZEX,SIZEY);
        return getRandomPorte(4,r);
    }

    public static Position getRandomPosition(int width, int heigth){
        int posX = rand.nextInt((width-1) - 0 + 1) + 0;
        int posY = rand.nextInt((heigth-1) - 0 + 1) + 0;
        return new Position(posX,posY);
    }

    public static Position getRandomPosition(int width, int heigth, Room r){
        return getRandomPosition(width-r.getSIZEX(),width-r.getSIZEY());
    }

    private static Room getRandomPorte(Room r){
        Position pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
        Cell currentCell = r.get(pos.getX(),pos.getY());
        while (currentCell.getType()!=Cell.CellType.BORDER){
            pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
            currentCell = r.get(pos.getX(),pos.getY());
        }
        r.set(pos.getX(),pos.getY(),new Cell(true, Cell.CellType.DOOR));
        return r;
    }

    public static Room getRandomPorte(int NbrPorteMax, Room r){
        int nbrPorte = rand.nextInt(NbrPorteMax - 1 + 1) + 1;
        Room room = r;
        for (int i = 0; i < nbrPorte; i++) {
            room = getRandomPorte(room);
        }
        return room;
    }

    public static Map getRamdomRooms(Map map){
        int nbrRooms=0;
        while(nbrRooms<5){
            Room r = getRandomRoom();
            Position pos = getRandomPosition(map.getSIZEX(),map.getSIZEY(),r);
            try {
                map.addRoom(r, pos);
                nbrRooms=nbrRooms+1;
            }
            catch (CollisionRoom e){
                continue;
            }
        }
        return map;
    }

}
