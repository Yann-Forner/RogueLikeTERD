package Model.Map;

import Model.Utils.Position;
import Model.Map.Room_Strategy.RoomStrategy;

public class Room extends Etage implements Comparable<Room> {
    private Position AbsolutePos=null;
    private final RoomStrategy strategy;

    public Room(int width, int height,RoomStrategy strategy){
        super(width,height);
        this.strategy=strategy;
        strategy.composeRoom(this);
    }

    public boolean noCollision(Etage etage){
        return strategy.noCollision(etage,this);
    }

    public int getNbrMaxMobPerRoom() {
        return strategy.getNbrMaxMobPerRoom();
    }

    public void setAbsolutePos(Position pos){
        AbsolutePos=pos.copyOf();
    }

    public Position getAbsolutePos() {
        return AbsolutePos.copyOf();
    }

    @Override
    public int compareTo(Room o) {
        return (int) (getAbsolutePos().Distance(new Position(0,0)) - o.getAbsolutePos().Distance(new Position(0,0)));
    }
}
