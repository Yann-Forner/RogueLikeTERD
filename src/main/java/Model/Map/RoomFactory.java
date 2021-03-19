package Model.Map;

import Model.Map.Room;
import Model.Map.Strategy.MarchandRoomStrategy;
import Model.Map.Strategy.NormalRoomStrategy;
import Model.Map.Strategy.TresorRoomStrategy;
import Model.Utils.Procedure;

public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,BOSS,MARCHAND,REPOS,TRAP
    }

    private static Room getNewMarchandRoom(){
        return new Room(5,5,0,new MarchandRoomStrategy());
    }

    private static Room getNewNormalRoom(){
        return Procedure.getRandomRoom(5,15,5,new NormalRoomStrategy());
    }

    public Room getNewRoom(roomType r, int width , int height, int nbrMaxMob){
        switch (r) {
            case TRESOR -> {
                return new Room(width,height,nbrMaxMob,new TresorRoomStrategy());
            }
            default -> {
                return new Room(width,height,nbrMaxMob,new NormalRoomStrategy());
            }
        }
    }

    public static Room getNewRoom(roomType r){
        switch (r){
            case MARCHAND -> {
                return getNewMarchandRoom();
            }
            default -> {
                return getNewNormalRoom();
            }
        }
    }
}
