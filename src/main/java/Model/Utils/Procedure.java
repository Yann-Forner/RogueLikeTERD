package Model.Utils;

import Exceptions.CollisionRoom;
import Model.Map.Cell;
import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Map.Strategy.NormalRoomStrategy;

import java.util.Collections;
import java.util.Random;

public class Procedure {
    private static final Random rand=new Random();

    /**
     * Seed de la Map.
     * @param seed long
     */
    public static void setSeed(long seed){
        rand.setSeed(seed);
    }

    /**
     * Renvoit une Position random : new Position( MaxWidth & MinWidth , MaxHeigtth & MinHeigth).
     * @param MaxWidth int
     * @param MaxHeigth int
     * @param MinWidth int
     * @param MinHeigth int
     * @return Position
     */
    private static Position getRandomPosition(int MaxWidth, int MaxHeigth, int MinWidth, int MinHeigth) {
        int posX = rand.nextInt((MaxWidth - MinWidth)) + MinWidth;
        int posY = rand.nextInt((MaxHeigth - MinHeigth)) + MinHeigth;
        return new Position(posX, posY);
    }

    /**
     * Renvoit une position random dans l'Etage e.
     * @param e Etage
     * @return Position
     */
    public static Position getRandomPosition(Etage e){
        return getRandomPosition(e.getWidth(),e.getHeigth(),1,1);
    }

    /**
     * Renvoit une position random dans la Room r.
     * @param r Room
     * @return Position
     */
    public static Position getRandomPosition(Room r){
        Position abs = r.getAbsolutePos();
        return getRandomPosition(r.getWidth()+abs.getX(),r.getHeigth()+abs.getY(),abs.getX(), abs.getY());
    }

    /**
     * Appele getRandomPosition(Room) ou getRandomPosition(Etage)
     * @param e Etage
     * @param r Room
     * @return Position
     */
    private static Position getRandomPosition(Etage e,Room... r){
        if (r.length == 1) {
            return getRandomPosition(r[0]);
        } else {
            return getRandomPosition(e);
        }
    }

    /**
     * Renvoit une Position aleatoire dans l'Etage/Room accesible, si isEntityGeneration est vrai
     * alors la position est aussi sans Entity et non Reservé.
     * @param isEntityGeneration boolean
     * @param e Etage
     * @param r Room
     * @return Position
     */
    //TODO comment faire ça sans le getRandomPosition(Etage e, Room... r) ?
    public static Position getAccesibleRandomPosition(boolean isEntityGeneration,Etage e,Room... r){
        Position pos = getRandomPosition(e,r);
        if(isEntityGeneration){
            Cell c = e.get(pos);
            while(!c.isAccesible() || c.getEntity()!=null || c.isReserved()){
                pos = getRandomPosition(e,r);
                c = e.get(pos);
            }
        }
        else{
            while(!e.get(pos).isAccesible()){
                pos = getRandomPosition(e,r);
            }
        }
        return pos;
    }

    /**
     * Renvoit une Room generé aleatoirement.
     * @param MinSize int
     * @param MaxSize int
     * @param nbrMaxMobPerRoom int
     * @return Room
     */
    public static Room getRandomRoom(int MinSize, int MaxSize, int nbrMaxMobPerRoom) {
        Position pos = getRandomPosition(MaxSize, MaxSize, MinSize, MinSize);
        return new Room(pos.getX(),pos.getY(),nbrMaxMobPerRoom,new NormalRoomStrategy());
    }

    /**
     * Genere nbrMaxRooms dans l'Etage.
     * @param etage Etage
     * @param nbrMaxRooms int
     */
    public static void setRandomRooms(Etage etage, int nbrMaxRooms) {
        int nbrRooms = 0;
        while (nbrRooms < nbrMaxRooms) {
            Room r = getRandomRoom(5,20,5);
            Position pos = getRandomPosition(etage.getWidth()-1 - r.getWidth(), etage.getHeigth()-1 - r.getHeigth(),1,1).somme(1,1);
            try {
                etage.addRoom(r, pos);
                r.setAbsolutePos(pos);
                nbrRooms++;
            } catch (CollisionRoom e) {}
        }
        Collections.sort(etage.getRooms());
    }

    /**
     * Genere un mob dans une Position aleatoire dans la Room.
     * @param r Room
     * @param etage Etage
     */
    private static void setRandomMob(Room r, Etage etage) {
        Position pos = getAccesibleRandomPosition(true,etage,r);
        Cell cell = etage.get(pos);
        Entity e=new BasicPlayer(etage,pos){
            @Override
            public String toString() {
                if(System.getProperty("os.name").equals("Linux")){
                    return "\uD83D\uDC7B";
                }
                else{
                    return Affichage.GREEN+Affichage.BOLD+"W";
                }
            }
        };
        cell.setEntity(e);
        etage.addEntity(e);
    }

    /**
     * Genere tous les mobs de l'etage.
     * @param etage Etage
     */
    public static void setRandomMob(Etage etage) {
        for (Room r : etage.getRooms()) {
            int nbrMobs = rand.nextInt(r.getNbrMaxMobPerRoom())+1;
            for (int i = 0; i < nbrMobs; i++) {
                setRandomMob(r, etage);
            }
        }
    }

    /**
     * Genere nbr coffres dans l'etage.
     * @param etage Etage
     * @param nbr int
     */
    public static void setRandomChest(Etage etage, int nbr){
        for (int i = 0; i < nbr; i++) {
            etage.get(getAccesibleRandomPosition(false,etage)).updateCell(true, Cell.CellType.CHEST);
        }
    }

    /**
     * Genere les escaliers aleatoirement dans un etage.
     * @param etage Etage
     */
    public static void setRandomUPnDOWN(Etage etage) {
        setRandomUP(etage);
        setRandomDOWN(etage);
    }

    /**
     * Escalier vers l'etage du dessus.
     * @param etage Etage
     */
    private static void setRandomUP(Etage etage){
        Position p1 = getAccesibleRandomPosition(false, etage);
        etage.get(p1).updateCell(true, Cell.CellType.UP);
    }

    /**
     * Escalier vers l'etage du dessous.
     * @param etage Etage
     */
    private static void setRandomDOWN(Etage etage){
        Position p2 = getAccesibleRandomPosition(false, etage);
        etage.get(p2).updateCell(true, Cell.CellType.DOWN);
    }

    /**
     * Genere un Etage de base.
     * @param etage Etage
     */
    public static void BasicEtage(Etage etage){
        Procedure.setRandomRooms(etage,8);
        etage.RoomFusion();
        Procedure.setRandomChest(etage,3);
        Procedure.setRandomUPnDOWN(etage);
        Position accesibleRandomPosition = getAccesibleRandomPosition(false, etage);
        etage.get(accesibleRandomPosition).updateCell(true, Cell.CellType.TRAP_ROOM);
        Procedure.setRandomMob(etage);
    }

    /**
     * Genere un Etage piege.
     * @param etage Etage
     */
    public static void TrapEtage(Etage etage){
        Procedure.setRandomRooms(etage,3);
        etage.RoomFusion();
        Procedure.setRandomUP(etage);
        Procedure.setRandomMob(etage);
    }


    public static Room RandomRoomType(RoomFactory factory){
        RoomFactory.roomType type=null;
        RoomFactory.roomType[] values = RoomFactory.roomType.values();
        int index = rand.nextInt(values.length);
        int acc = 0;
        for (RoomFactory.roomType t : values) {
            if(acc == index){
                type=t;
                break;
            }
            acc++;
        }
        return factory.getNewRoom(type, rand.nextInt((20 - 5)) + 5, rand.nextInt((20 - 5)) + 5, 5);
    }



}