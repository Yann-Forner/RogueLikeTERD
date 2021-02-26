import Exceptions.CollisionRoom;
import Model.*;

import java.util.Random;

public class Procedure {
    private final static Random rand =new Random();

    /**
     * Renvoit une Room generée proceduralement.
     * @return Room
     */
    public static Room getRandomRoom(){
        int SIZEX = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        int SIZEY = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        Room r = new Room(SIZEX,SIZEY);
        setRandomPorte(4,r);
        return r;
    }

    /**
     * Renvoit une Position aleatoire entre width et heigth.
     * **/
    private static Position getRandomPosition(int width, int heigth){
        int posX = rand.nextInt((width - 1) + 1);
        int posY = rand.nextInt((heigth - 1) + 1);
        return new Position(posX,posY);
    }

    /**
     * Renvoit une Position aleatoire dans la Room r.
     * @param r Room
     * @return Position
     */
    //TODO optimiser ça pas ouf le while
    public static Position getRandomPosition(Room r){
        Position pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
        while((r.get(pos).getType().equals(Cell.CellType.BORDER)) || (r.get(pos).getType().equals(Cell.CellType.ANGLE)) || (r.get(pos).getType().equals(Cell.CellType.DOOR))){
            pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
        }
        return pos.somme(r.getAbsolutePos());
    }

    /**
     * Renvoit une porte(CellType.DOOR) aleatoire sur la bordure de la Room r.
     * @param r Room
     */
    private static void setRandomPorte(Room r){
        Position pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
        Cell currentCell = r.get(pos.getX(),pos.getY());
        while (currentCell.getType()!=Cell.CellType.BORDER){
            pos = getRandomPosition(r.getSIZEX(),r.getSIZEY());
            currentCell = r.get(pos.getX(),pos.getY());
        }
        r.set(pos.getX(),pos.getY(),new Cell(true, Cell.CellType.DOOR));
        r.addDoors(pos);
    }

    /**
     * Renvoit NbrPorteMax portes(CellType.DOOR) aleatoire sur la bordure de la Room r.
     * @param NbrPorteMax int
     * @param r r
     */
    public static void setRandomPorte(int NbrPorteMax, Room r){
        int nbrPorte = rand.nextInt(NbrPorteMax - 1 + 1) + 1;
        for (int i = 0; i < nbrPorte; i++) {
            setRandomPorte(r);
        }
    }

    /**
     * Genere puis ajoute les Rooms generée proceduralement dans la Map.
     * @param map Map
     */
    public static void setRamdomRooms(Map map){
        int nbrRooms=0;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while(elapsedTime<2000 && nbrRooms<Map.nbrMaxRooms){
            elapsedTime = System.currentTimeMillis() - startTime;
            Room r = getRandomRoom();
            Position pos = getRandomPosition(map.getSIZEX()-r.getSIZEX(),map.getSIZEY()-r.getSIZEY());
            try {
                map.addRoom(r, pos);
                r.setAbsolutePos(pos);
                nbrRooms=nbrRooms+1;
            }
            catch (CollisionRoom e){
                break;
            }
        }
    }

    /**
     * Entity aleatoire dans la Room r.
     * @param r Room
     * @param map Map
     */
    private static void setRandomMob(Room r,Map map){
        Position pos=getRandomPosition(r);
        Cell cell=map.get(pos);
        cell.setEntity(new BasicPlayer(pos,r));
    }

    /**
     * Generation aleatoire des Entity dans toutes les Rooms de la Map.
     * @param map Map
     */
    public static void setRamdomMob(Map map){
        for (Room r : map.getRooms()){
            int nbrMobs = rand.nextInt(Room.nbrMaxMobPerRoom + 1);
            for (int i = 0; i < nbrMobs; i++) {
                setRandomMob(r,map);
            }
        }
    }

}
