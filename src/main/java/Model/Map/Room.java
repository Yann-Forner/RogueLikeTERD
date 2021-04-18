package Model.Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Utils.Position;
import Model.Map.Room_Strategy.RoomStrategy;

public class Room extends Etage implements Comparable<Room> {
    private Position AbsolutePos=null;
    public final RoomStrategy strategy;

    public Room(int width, int height,RoomStrategy strategy){
        super(width,height);
        this.strategy=strategy;
        strategy.composeRoom(this);
    }

    public void setMonsters(Etage etage){
        strategy.setMonsters(this);
        for(AbstractMonster a : Monsters){
            a.setEtage(etage);
            a.setPosition(a.getPosition().somme(getAbsolutePos()));
            etage.addMonster(a);
        }
        Monsters.clear();
    }

    public void setItems(Etage etage) {
        strategy.setItems(this);
        for(AbstractItem a : Items) {
            a.setEtage(etage);
            a.setPosition(a.getPosition().somme(getAbsolutePos()));
            etage.addItem(a);
        }
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
        //TODO on a encore beosin du comparable? #YANN
        return (int) (getAbsolutePos().Distance(new Position(0,0)) - o.getAbsolutePos().Distance(new Position(0,0)));
    }
}
