package Model.Utils;

import Exceptions.CollisionRoom;
import Model.Entitys.AbstractMonster;
import Model.Entitys.MonsterFactory;
import Model.Map.Cell;
import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Map.Room_Strategy.RoomStrategy;

import java.util.ArrayList;
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

    public static int getRandomInt(int Max, int Min){
        return rand.nextInt(Max-Min)+Min;
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
     * Renvoit une position random absolue dans la Room r.
     * @param r Room
     * @return Position
     */
    public static Position getRandomPosition(Room r){
        Position abs = r.getAbsolutePos();
        return getRandomPosition(r.getWidth()+abs.getX(),r.getHeigth()+abs.getY(),abs.getX(), abs.getY());
    }

    /**
     * Renvoit une position random dans la Room r.
     * @param r Room
     * @return Position
     */
    public static Position getRelativeRandomPosition(Room r){
        return getRandomPosition(r.getWidth(),r.getHeigth(),0,0);
    }

    /**
     * Renvoit un mur Random dans la Room r.
     * @param r Room
     * @return Position
     */
    public static Position getRandomWall(Room r){
        Position pos = Procedure.getRelativeRandomPosition(r);
        while((pos.getX()!=0
                && pos.getX()!= r.getWidth()
                && pos.getY()!=0
                && pos.getY()!=r.getHeigth())
                || pos.equals(new Position(0,0))
                || pos.equals(new Position(r.getWidth()-1,0))
                || pos.equals(new Position(0,r.getHeigth()-1))
                || pos.equals(new Position(r.getWidth()-1,+r.getHeigth()-1))){
            pos =  Procedure.getRelativeRandomPosition(r);
        }
        return pos;
    }

    /**
     * Renvoit une Position aleatoire dans l'Etage/Room accesible, si isEntityGeneration est vrai
     * alors la position est aussi sans Entity et non Reservé.
     * @param isEntityGeneration boolean
     * @param e Etage
     * @param r Room ...
     * @return Position
     */
    //TODO comment faire ça sans le getRandomPosition(Etage e, Room... r) ?
    public static Position getAccesibleRandomPosition(boolean isEntityGeneration,Etage e,Room ... r){
        Position pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
        if(isEntityGeneration){
            Cell c = e.get(pos);
            while(!c.isAccesible() || c.getEntity()!=null || c.isReserved()){
                pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
                c = e.get(pos);
            }
        }
        else{
            while(!e.get(pos).isAccesible()){
                pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
            }
        }
        return pos;
    }

    /**
     * Renvoit une Room generé aleatoirement.
     * @param MinSize int
     * @param MaxSize int
     * @return Room
     */
    public static Room getRandomRoom(int MinSize, int MaxSize, RoomStrategy strategy) {
        Position pos = getRandomPosition(MaxSize, MaxSize, MinSize, MinSize);
        return new Room(pos.getX(),pos.getY(),strategy);
    }
    /**
     * Renvoit une Room generé aleatoirement avec une taille impaire pour avoir un centre.
     * @param MinSize int
     * @param MaxSize int
     * @return Room
     */
    public static Room getRandomImpairSizeRoom(int MinSize, int MaxSize, RoomStrategy strategy) {
        int size = rand.nextInt((MaxSize - MinSize)) + MinSize;
        if(size%2==0)++size;
        return new Room(size,size,strategy);
    }


    public static void setRandomRooms(Etage etage,EtageStrategy etageStrategy ,ArrayList<RoomFactory.roomType> roomTypes ){
        int nbrRooms = 0;
        long t1 = System.currentTimeMillis();
        while (nbrRooms < etageStrategy.getNbrMaxRoom() && nbrRooms < roomTypes.size()  &&  (System.currentTimeMillis()-t1<500) ){
            Room r = RoomFactory.getNewRoom(roomTypes.get(nbrRooms));
            Position pos =  getRandomPosition(etage.getWidth()-1 - r.getWidth(), etage.getHeigth()-1 - r.getHeigth(),1,1).somme(1,1);
            try {
                r.setAbsolutePos(pos);
                etage.addRoom(r);
                nbrRooms++;
            }catch (CollisionRoom e){

            }
        }
        Collections.sort(etage.getRooms());
    }


    /**
     * Genere nbrMaxRooms dans l'Etage.
     * @param etage Etage
     * @param type RoomFactory.roomType
     */
    public static void setRandomRooms(Etage etage, EtageStrategy etageStrategy, RoomFactory.roomType type) {
        int nbrRooms = 0;
        long t1 = System.currentTimeMillis();
        while (nbrRooms < etageStrategy.getNbrMaxRoom() && (System.currentTimeMillis()-t1<500)) {
            Room r = RoomFactory.getNewRoom(type);
           Position pos;
            if (etageStrategy.getNbrMaxRoom() == 1) {
                 pos = new Position(((etage.getWidth()-1)/2)- r.getWidth()/2,(etage.getHeigth())/2- r.getHeigth()/2);
            }else {
                 pos = getRandomPosition(etage.getWidth()-1 - r.getWidth(), etage.getHeigth()-1 - r.getHeigth(),1,1).somme(1,1);
            }
            try {
                r.setAbsolutePos(pos);
                etage.addRoom(r);
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
        //TODO passer le montre en parametre
        AbstractMonster m = MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.GHOST);
        cell.setEntity(m);
        etage.addMonster(m);
    }

    /**
     * Genere tous les mobs de l'etage.
     * @param etage Etage
     */
    public static void setRandomMob(Etage etage) {
        for (Room r : etage.getRooms()) {
            int nbrMobs = r.getNbrMaxMobPerRoom()==0 ? 0 : rand.nextInt(r.getNbrMaxMobPerRoom())+1;
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
    public static void setRandomUP(Etage etage){
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

}