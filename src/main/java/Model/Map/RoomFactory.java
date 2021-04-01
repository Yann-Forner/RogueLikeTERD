package Model.Map;

import Model.Map.Room_Strategy.*;
import Model.Utils.Procedure;

public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,CIRCLENORMAL,MINIBOSS,BOSS,MARCHAND,REPOS,TRAP
    }
    private static Room getNewMiniBossRoom(){return Procedure.getRandomImpairSizeRoom(11,15,new MiniBossRoomStrategy());}

    private static Room getNewBigBossRoom(){return Procedure.getRandomImpairSizeRoom(19,36,new BigBossRoomStrategy());}

    private static Room getNewMarchandRoom(){
        return new Room(5,5,new MarchandRoomStrategy());
    }

    private static Room getNewNormalRoom(){
        return Procedure.getRandomRoom(5,15,new NormalRoomStrategy());
    }

    private static Room getNewTrapRoom(){
        return Procedure.getRandomRoom(5,10,new TrapRoomStrategy());
    }

    private static Room getNewTresorRoom(){return Procedure.getRandomRoom(6,10,new TresorRoomStrategy());}

    private static Room getNewReposRoom(){return new Room(10,10,new ReposRoomStrategy());}

    private static Room getNewCircleRoom(){return Procedure.getRandomImpairSizeRoom(11,15,new NormalCircleRoom());}

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
            default -> {
                return getNewNormalRoom();
            }
        }
    }
}
