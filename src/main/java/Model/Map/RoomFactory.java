package Model.Map;

import Model.Map.Room_Strategy.MarchandRoomStrategy;
import Model.Map.Room_Strategy.NormalRoomStrategy;
import Model.Map.Room_Strategy.TrapRoomStrategy;
import Model.Map.Room_Strategy.TresorRoomStrategy;
import Model.Utils.Procedure;

public class RoomFactory {
    public enum roomType {
        TRESOR,NORMAL,BOSS,MARCHAND,REPOS,TRAP
    }

    private static Room getNewMarchandRoom(){
        return new Room(5,5,new MarchandRoomStrategy());
    }

    private static Room getNewNormalRoom(){
        return Procedure.getRandomRoom(5,15,new NormalRoomStrategy());
    }

    private static Room getNewTrapRoom(){
        return Procedure.getRandomRoom(5,10,new TrapRoomStrategy());
    }

    public Room getNewRoom(roomType r, int width , int height){
        switch (r) {
            case TRESOR -> {
                return new Room(width,height,new TresorRoomStrategy());
            }
            default -> {
                return new Room(width,height,new NormalRoomStrategy());
            }
        }
    }

    public static Room getNewRoom(roomType r){
        switch (r){
            case MARCHAND -> {
                return getNewMarchandRoom();
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
