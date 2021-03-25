package Model.Map;

import Model.Entitys.BasicPlayer;
import Model.Entitys.Ghost;
import Model.Entitys.MonsterFactory;
import Model.Map.Etage_Strategy.NormalEtageStrategy;
import Model.Map.Etage_Strategy.TrapEtageStrategy;
import Model.Utils.Position;
import Model.Utils.Procedure;

import java.util.ArrayList;

public class Map {
    public ArrayList<Etage> etages = new ArrayList<>();
    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;
    private boolean inTemporaryEtage=false;
    private final BasicPlayer player;

    public Map(){
        Etage etage=new Etage(MapWidth,MapHeigth, new NormalEtageStrategy());
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        player=new BasicPlayer(etage,pos);
        etage.get(pos).setEntity(player);

        Ghost g = MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.GHOST);
        etage.addEntity(g);
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
            etage=new Etage(MapWidth,MapHeigth, new NormalEtageStrategy());
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        getPlayer().updateEtage(etage,pos);
    }

    public void UP(){
        if(inTemporaryEtage){
            Etage etage=etages.get(etages.size()-1);
            Position pos = Procedure.getAccesibleRandomPosition(true,etage);
            getPlayer().updateEtage(etage,pos);
            inTemporaryEtage=false;
        }
        else{
            int currentIndex = getIndexCurrent();
            if(currentIndex!=0){
                Etage etage=etages.get(currentIndex-1);
                Position pos = Procedure.getAccesibleRandomPosition(true,etage);
                getPlayer().updateEtage(etage,pos);
            }
        }
    }

    public void TRAP_ROOM(){
        Etage etage = new Etage(MapWidth,MapHeigth,new TrapEtageStrategy());
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        getPlayer().updateEtage(etage,pos);
        inTemporaryEtage=true;
    }

}
