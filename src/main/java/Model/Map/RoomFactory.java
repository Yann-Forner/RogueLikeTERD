package Model.Map;

import Model.Map.Room;
import Model.Map.Strategy.NormalRoomStrategy;
import Model.Map.Strategy.TresorRoomStrategy;

public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,BOSS,MARCHAND,REPOS,TRAP
    }
    public Room getNewTreasorRoom(){
        return new  Room(5,5,0,new TresorRoomStrategy());
    }
    public Room getNewRoom(roomType r, int width , int height, int nbrMaxMob){
        switch (r){
            case TRESOR -> {
                return getNewTreasorRoom();
            }
            default -> {
                return new Room(width,height,nbrMaxMob,new NormalRoomStrategy());
            }
        }
    }
}
