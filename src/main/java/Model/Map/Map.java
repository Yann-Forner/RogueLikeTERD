package Model.Map;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage_Strategy.BossEtageStategy;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Etage_Strategy.TrapEtageStrategy;
import Model.Utils.Start;
import Model.Utils.Position;
import Model.Utils.Procedure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Map implements Serializable {
    private final ArrayList<Etage> etages = new ArrayList<>();
    public final int MapWidth = 40;
    public final int MapHeigth = 40;
    private boolean inTemporaryEtage=false;

    public Map(BasicPlayer player){
        Etage etage = new Etage(MapWidth, MapHeigth, EtageStrategy.getRandomStrategy());
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        etage.get(pos).setEntity(player);
        player.setEtage(etage);
        player.setPosition(pos);
    }

    public Etage getCurrent(){
        return Objects.requireNonNull(Start.getPlayer()).getEtage();
    }

    public int getIndexCurrent(){
        return etages.indexOf(getCurrent());
    }

    public void DOWN(){
        Etage etage;
        int currentIndex = getIndexCurrent();
        if(currentIndex == etages.size()-1){
            EtageStrategy strategy = currentIndex%10==0 ? new BossEtageStategy() : EtageStrategy.getRandomStrategy();
            etage=new Etage(MapWidth,MapHeigth, strategy);
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
    }

    public void UP(){
        if(inTemporaryEtage){
            Etage etage=etages.get(etages.size()-1);
            Position pos = Procedure.getAccesibleRandomPosition(true,etage);
            Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
            inTemporaryEtage=false;
        }
        else{
            int currentIndex = getIndexCurrent();
            if(currentIndex!=0){
                Etage etage=etages.get(currentIndex-1);
                Position pos = Procedure.getAccesibleRandomPosition(true,etage);
                Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
            }
        }
    }

    public void TRAP_ROOM(){
        //TODO revenir a l'etage de depart en remontant et non le dernier index de l'arraylist
        Etage etage = new Etage(MapWidth,MapHeigth,new TrapEtageStrategy());
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
        inTemporaryEtage=true;
    }

    public ArrayList<Etage> getEtages(){
        return etages;
    }
}
