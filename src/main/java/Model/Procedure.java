package Model;

import Exceptions.CollisionRoom;
import Model.Map.Etage;
import Model.Map.Room;

import java.util.Collections;
import java.util.Random;

public class Procedure {
    private static final Random rand=new Random();

    public static void setSeed(long seed){
        rand.setSeed(seed);
    }

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
     * @param e Etage
     * @return Position
     */
    public static Position getAccesibleRandomPosition(Etage e) {
        Position pos = getRandomPosition(e.getSIZEX(),e.getSIZEY());
        Cell c = e.get(pos);
        while(!c.isAccesible() && c.getEntity()==null && !e.isReserved(c)){
            pos = getRandomPosition(e.getSIZEX(),e.getSIZEY());
            c = e.get(pos);
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
    public static void setRandomRooms(Etage etage, int nbrMaxRooms) {
        int nbrRooms = 0;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (elapsedTime < 2000 && nbrRooms < nbrMaxRooms) {
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
     * @param r Room
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
            etage.get(getAccesibleRandomPosition(etage)).updateCell(true, Cell.CellType.CHEST);
        }
    }

    /**
     * Genere les escaliers aleatoirement dans un etage.
     * @param etage Etage
     */
    public static void setRandomUPnDOWN(Etage etage) {
        Position p1 = getAccesibleRandomPosition(etage);
        Position p2 = getAccesibleRandomPosition(etage);
        etage.get(p1).updateCell(true, Cell.CellType.UP);
        etage.get(p2).updateCell(true, Cell.CellType.DOWN);
    }

    /**
     * Genere un Etage de base #TYPE1
     * @param etage Etage
     */
    public static void BasicEtage(Etage etage){
        Procedure.setRandomRooms(etage,8);
        etage.RoomFusion();
        Procedure.setRandomChest(etage,3);
        Procedure.setRandomUPnDOWN(etage);
        Procedure.setRandomMob(etage);
    }
}