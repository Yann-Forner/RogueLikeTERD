package Model.Map;

import Model.Map.Room_Strategy.*;
import Model.Utils.Procedure;

/**
 * Factory des types de Rooms.
 * @author Yann
 */
public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,CIRCLENORMAL,MINIBOSS,BOSS,MARCHAND,REPOS,TRAP,TRIANGLENORMAL
    }
    private static Room getNewMiniBossRoom(){return Procedure.getRandomImpairSizeRoom(11,15,new MiniBossRoomStrategy());}

    private static Room getNewBigBossRoom(){return Procedure.getRandomImpairSizeRoom(19,36,new BigBossRoomStrategy());}

    private static Room getNewNormalTriangleRoom(){ return Procedure.getRandomRoom(10,20,new NormalTriangleStrategy());}

    private static Room getNewMarchandRoom(){
        return new Room(10,10,new MarchandRoomStrategy());
    }

    private static Room getNewNormalRoom(){
        return Procedure.getRandomRoom(5,15,new NormalRoomStrategy());
    }

    private static Room getNewTrapRoom(){
        return Procedure.getRandomRoom(5,10,new TrapRoomStrategy());
    }

    private static Room getNewTresorRoom(){return Procedure.getRandomRoom(6,10,new TresorRoomStrategy());}

    private static Room getNewReposRoom(){return new Room(9,3,new ReposRoomStrategy());}

    private static Room getNewCircleRoom(){return Procedure.getRandomImpairSizeRoom(11,15,new NormalCircleRoomStrategy());}

    /**
     * Renvoit une nouvelle Room selon le type passé en parametre.
     * @param r Type de room
     * @return Room
     * @author Yann
     */
    public static Room getNewRoom(roomType r){
        switch (r){
            case MINIBOSS -> {
                return getNewMiniBossRoom();
            }
            case BOSS -> {
                return getNewBigBossRoom();
            }
            case REPOS -> {
                return getNewReposRoom();
            }
            case TRESOR -> {
                return getNewTresorRoom();
            }
            case MARCHAND -> {
                return getNewMarchandRoom();
            }
            case CIRCLENORMAL -> {
                return getNewCircleRoom();
            }
            case TRAP -> {
                return getNewTrapRoom();
            }
            case TRIANGLENORMAL -> {
                return getNewNormalTriangleRoom();
            }
            default -> {
                return getNewNormalRoom();
            }
        }
    }
}
