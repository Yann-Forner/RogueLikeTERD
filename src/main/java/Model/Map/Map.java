package Model.Map;

import Model.*;

import java.util.ArrayList;

public class Map {
    public ArrayList<Etage> etages = new ArrayList<>();
    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;
    private final BasicPlayer player;

    public Map(){
        Etage etage=new Etage(MapWidth,MapHeigth);
        Procedure.BasicEtage(etage);
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(etage);
        player=new BasicPlayer(etage,pos);
        etage.get(pos).setEntity(player);
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
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(etage);
        getPlayer().update(etage,pos);
    }

    public void UP(){
        int currentIndex = getIndexCurrent();
        if(currentIndex!=0){
            Etage etage=etages.get(currentIndex-1);
            Position pos = Procedure.getAccesibleRandomPosition(etage);
            getPlayer().update(etage,pos);
        }
    }

}
