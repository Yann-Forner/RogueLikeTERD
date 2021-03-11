package Model;

import Exceptions.CollisionRoom;

import java.util.Collections;
import java.util.Random;

public class Procedure {
    private final static Random rand = new Random();

    /**
     * Renvoit une Room generée proceduralement.
     * @return Room
     */
    public static Room getRandomRoom() {
        int SIZEX = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        int SIZEY = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        return new Room(SIZEX, SIZEY);
    }

    /**
     * Renvoit une Position aleatoire entre width et heigth.
     * @param width int
     * @param heigth int
     * @return Position
     */
    private static Position getRandomPosition(int width, int heigth) {
        int posX = rand.nextInt((width - 1) + 1);
        int posY = rand.nextInt((heigth - 1) + 1);
        return new Position(posX, posY);
    }

    /**
     * Renvoit une position aleatoire dans la map qui est accesible.
     * @param m Map
     * @return Position
     */
    public static Position getAccesibleRandomPosition(Map m) {
        Position pos = getRandomPosition(m.SIZEX,m.SIZEY);
        Cell c = m.get(pos.getX(),pos.getY());
        while(!c.isAccesible() && c.getEntity()==null){
            pos = getRandomPosition(m.SIZEX,m.SIZEY);
            c = m.get(pos.getX(),pos.getY());
        }
        return pos;
    }

    /**
     * Renvoit une Position aleatoire dans la Room r.
     * @param r Room
     * @return Position
     */
    //TODO optimiser ça pas ouf le while
    public static Position getRandomPosition(Room r) {
        Position pos = getRandomPosition(r.getSIZEX(), r.getSIZEY());
        while (!r.get(pos).isAccesible()) {
            pos = getRandomPosition(r.getSIZEX(), r.getSIZEY());
        }
        return pos.somme(r.getAbsolutePos());
    }

    /**
     * Genere puis ajoute les Rooms generée proceduralement dans la Map.
     * @param map Map
     */
    public static void setRandomRooms(Map map) {
        int nbrRooms = 0;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (elapsedTime < 2000 && nbrRooms < Map.nbrMaxRooms) {
            elapsedTime = System.currentTimeMillis() - startTime;
            Room r = getRandomRoom();
            Position pos = getRandomPosition(map.getSIZEX() - r.getSIZEX(), map.getSIZEY() - r.getSIZEY());
            try {
                Collections.sort(map.getRooms());
                map.addRoom(r, pos);
                r.setAbsolutePos(pos);
                nbrRooms++;
            } catch (CollisionRoom e) {}
        }
    }

    /**
     * Entity aleatoire dans la Room r.
     * @param r   Room
     * @param map Map
     */
    private static void setRandomMob(Room r, Map map) {
        Position pos = getRandomPosition(r);
        Cell cell = map.get(pos);
        Entity e=new BasicPlayer(map,pos){
            @Override
            public String toString() {
                return "\u001B[36mM";
            }
        };
        cell.setEntity(e);
        map.addEntity(e);
    }

    /**
     * Generation aleatoire des Entity dans toutes les Rooms de la Map.
     * @param map Map
     */
    public static void setRandomMob(Map map) {
        for (Room r : map.getRooms()) {
            int nbrMobs = rand.nextInt(Room.nbrMaxMobPerRoom)+1;
            for (int i = 0; i < nbrMobs; i++) {
                setRandomMob(r, map);
            }
        }
    }

    /**
     * Genere nbr Coffres sur la map a des positions aleatoires.
     * @param map Map
     * @param nbr int
     */
    public static void setRandomChest(Map map,int nbr){
        for (int i = 0; i < nbr; i++) {
            map.get(getAccesibleRandomPosition(map)).updateCell(true, Cell.CellType.CHEST);
        }
    }

}