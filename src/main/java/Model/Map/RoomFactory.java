package Model.Map;

import Model.Map.Room;
import Model.Map.Strategy.MarchandRoomStrategy;
import Model.Map.Strategy.NormalRoomStrategy;
import Model.Map.Strategy.TresorRoomStrategy;

public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,BOSS,MARCHAND,REPOS,TRAP
    }

    public Room getNewMarchandRoom(){
        return new  Room(5,5,0,new MarchandRoomStrategy());
    }
    public Room getNewRoom(roomType r, int width , int height, int nbrMaxMob){
        switch (r){
            case MARCHAND -> {
                return getNewMarchandRoom();
            }
            case TRESOR -> {
                return new Room(width,height,nbrMaxMob,new TresorRoomStrategy());
            }
            default -> {
                return new Room(width,height,nbrMaxMob,new NormalRoomStrategy());
            }
        }
    }
}
