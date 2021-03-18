package Model.Map;

import Model.Map.Room;
import Model.Map.Strategy.NormalRoomStrategy;

public class RoomFactory {
    public enum roomType {
        TREASOR,NORMAL,BOSS,MARCHAND,REPOS,TRAP
    }
    public Room getRoom(roomType r, int width , int height, int nbrMaxPerMob){
        switch (r){
            default -> {
                return new Room(width,height,nbrMaxPerMob,new NormalRoomStrategy());
            }
        }
    }
}
