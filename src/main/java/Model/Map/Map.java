package Model.Map;

import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Etage_Strategy.TrapEtageStrategy;
import Model.Utils.Start;
import Model.Utils.Position;
import Model.Utils.Procedure;

import java.util.ArrayList;

public class Map {
    public ArrayList<Etage> etages = new ArrayList<>();
    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;
    private boolean inTemporaryEtage=false;

    public Map(){
        Etage etage=new Etage(MapWidth, MapHeigth, EtageStrategy.getRandomStrategy());
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        etage.get(pos).setEntity(Start.getPlayer());
        Start.getPlayer().setEtage(etage);
        Start.getPlayer().setPosition(pos);
    }

    public Etage getCurrent(){
        return Start.getPlayer().getEtage();
    }

    public int getIndexCurrent(){
        return etages.indexOf(getCurrent());
    }

    public void DOWN(){
        Etage etage;
        int currentIndex = getIndexCurrent();
        if(currentIndex == etages.size()-1){
            etage=new Etage(MapWidth,MapHeigth, EtageStrategy.getRandomStrategy());
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Start.getPlayer().updateEtage(etage,pos);
    }

    public void UP(){
        if(inTemporaryEtage){
            Etage etage=etages.get(etages.size()-1);
            Position pos = Procedure.getAccesibleRandomPosition(true,etage);
            Start.getPlayer().updateEtage(etage,pos);
            inTemporaryEtage=false;
        }
        else{
            int currentIndex = getIndexCurrent();
            if(currentIndex!=0){
                Etage etage=etages.get(currentIndex-1);
                Position pos = Procedure.getAccesibleRandomPosition(true,etage);
                Start.getPlayer().updateEtage(etage,pos);
            }
        }
    }

    public void TRAP_ROOM(){
        Etage etage = new Etage(MapWidth,MapHeigth,new TrapEtageStrategy());
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Start.getPlayer().updateEtage(etage,pos);
        inTemporaryEtage=true;
    }

}
