package Model.Map;

import Model.Utils.Position;
import Model.Map.Strategy.RoomStrategy;

public class Room extends Etage implements Comparable<Room> {
    private final int nbrMaxMobPerRoom;
    private Position AbsolutePos=null;
    private RoomStrategy strategy;

    public Room(int width, int height,int nbrMaxMobPerRoom,RoomStrategy strategy){
        super(width,height);
        this.nbrMaxMobPerRoom=nbrMaxMobPerRoom;
        this.strategy=strategy;
        strategy.composeRoom(this);
    }

    public boolean isCollision(Etage etage,Position pos){
        return strategy.isCollision(etage,this,pos);
    }

    public int getNbrMaxRoom() {
        return strategy.getNbrMaxRoom();
    }

    public void setAbsolutePos(Position pos){
        AbsolutePos=pos.copyOf();
    }

    public Position getAbsolutePos() {
        return AbsolutePos.copyOf();
    }

    public int getNbrMaxMobPerRoom() {
        return nbrMaxMobPerRoom;
    }

    @Override
    public int compareTo(Room o) {
        return (int) (this.getAbsolutePos().Distance(new Position(0,0)) - o.getAbsolutePos().Distance(new Position(0,0)));
    }
}
