package Model;

import Exceptions.CollisionRoom;
import Model.Map.Etage;
import Model.Map.Room;

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
     * Renvoit une position aleatoire dans la etage qui est accesible.
     * @param m Etage
     * @return Position
     */
    public static Position getAccesibleRandomPosition(Etage m) {
        Position pos = getRandomPosition(m.getSIZEX(),m.getSIZEY());
        Cell c = m.get(pos.getX(),pos.getY());
        while(!c.isAccesible() && c.getEntity()==null){
            pos = getRandomPosition(m.getSIZEX(),m.getSIZEY());
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
     * Genere puis ajoute les Rooms generée proceduralement dans la Etage.
     * @param etage Etage
     */
    public static void setRandomRooms(Etage etage) {
        int nbrRooms = 0;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (elapsedTime < 2000 && nbrRooms < Etage.nbrMaxRooms) {
            elapsedTime = System.currentTimeMillis() - startTime;
            Room r = getRandomRoom();
            Position pos = getRandomPosition(etage.getSIZEX()-1 - r.getSIZEX(), etage.getSIZEY()-1 - r.getSIZEY());
            pos=new Position(pos.getX()+1, pos.getY()+1);
            try {
                etage.addRoom(r, pos);
                r.setAbsolutePos(pos);
                nbrRooms++;
            } catch (CollisionRoom e) {}
        }
        Collections.sort(etage.getRooms());
    }

    /**
     * Entity aleatoire dans la Room r.
     * @param r   Room
     * @param etage Etage
     */
    private static void setRandomMob(Room r, Etage etage) {
        Position pos = getRandomPosition(r);
        Cell cell = etage.get(pos);
        Entity e=new BasicPlayer(etage,pos){
            @Override
            public String toString() {
                return "\u001B[36mM";
            }
        };
        cell.setEntity(e);
        etage.addEntity(e);
    }

    /**
     * Generation aleatoire des Entity dans toutes les Rooms de la Etage.
     * @param etage Etage
     */
    public static void setRandomMob(Etage etage) {
        for (Room r : etage.getRooms()) {
            int nbrMobs = rand.nextInt(Room.nbrMaxMobPerRoom)+1;
            for (int i = 0; i < nbrMobs; i++) {
                setRandomMob(r, etage);
            }
        }
    }

    /**
     * Genere nbr Coffres sur la etage a des positions aleatoires.
     * @param etage Etage
     * @param nbr int
     */
    public static void setRandomChest(Etage etage, int nbr){
        for (int i = 0; i < nbr; i++) {
            etage.get(getAccesibleRandomPosition(etage)).updateCell(false, Cell.CellType.CHEST);
        }
    }

}