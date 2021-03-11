package Model.Map;

import Model.*;

import java.util.ArrayList;

public class Map {
    public ArrayList<Etage> etages = new ArrayList<>();
    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;
    private final BasicPlayer player1;

    public Map(){
        Etage e1=new Etage(MapWidth,MapHeigth);
        Procedure.BasicEtage(e1);
        etages.add(e1);
        Position p1 = Procedure.getAccesibleRandomPosition(e1);
        player1=new BasicPlayer(e1,p1);
    }

    public Etage getCurrent(){
        return player1.getEtage();
    }

    public int getIndexCurrent(){
        return etages.indexOf(getCurrent());
    }

    public BasicPlayer getPlayer(){
        return player1;
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
        Position p = Procedure.getAccesibleRandomPosition(etage);
        getCurrent().get(player1.getPosition()).setEntity(null);
        player1.updateEtage(etage);
        player1.setPosition(p);
    }

    public void UP(){
        int currentIndex = etages.indexOf(getCurrent());
        if(currentIndex!=0){
            Etage etage=etages.get(currentIndex-1);
            Position p = Procedure.getAccesibleRandomPosition(etage);
            getCurrent().get(player1.getPosition()).setEntity(null);
            player1.updateEtage(etage);
            player1.setPosition(p);
        }
    }


}
