package Model.Map;

import Model.*;

import java.util.ArrayList;

public class Map {
    public ArrayList<Etage> etages = new ArrayList<>();
    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;
    private boolean inTemporaryEtage=false;
    private final BasicPlayer player;

    public Map(){
        Etage etage=new Etage(MapWidth,MapHeigth);
        Procedure.BasicEtage(etage);
        System.out.println("ok");
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        player=new BasicPlayer(etage,pos);
        etage.get(pos).setEntity(player);
        System.out.println("ok");
    }

    public Etage getCurrent(){
        return player.getEtage();
    }

    public int getIndexCurrent(){
        return etages.indexOf(getCurrent());
    }

    public BasicPlayer getPlayer(){
        return player;
    }

    public void DOWN(){
        Etage etage;
        int currentIndex = getIndexCurrent();
        if(currentIndex == etages.size()-1){
            etage=new Etage(MapWidth,MapHeigth);
            Procedure.BasicEtage(etage);
            //etage.addRoom(Procedure.RandomRoomType(new RoomFactory()),new Position(etage.getWidth()/2,etage.getHeigth()/2));
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        getPlayer().update(etage,pos);
    }

    public void UP(){
        if(inTemporaryEtage){
            Etage etage=etages.get(etages.size()-1);
            Position pos = Procedure.getAccesibleRandomPosition(true,etage);
            getPlayer().update(etage,pos);
            inTemporaryEtage=false;
        }
        else{
            int currentIndex = getIndexCurrent();
            if(currentIndex!=0){
                Etage etage=etages.get(currentIndex-1);
                Position pos = Procedure.getAccesibleRandomPosition(true,etage);
                getPlayer().update(etage,pos);
            }
        }
    }

    public void TRAP_ROOM(){
        Etage etage = new Etage(MapWidth,MapHeigth);
        Procedure.TrapEtage(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        getPlayer().update(etage,pos);
        inTemporaryEtage=true;
    }

}
