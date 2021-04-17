package Model.Entitys;

import Model.Map.Etage;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public class BasicPlayer extends Entity {
    private int MAX_EXP;
    private int CURRENT_EXP;

    public BasicPlayer(int vision_radius, String nom,int pv,int force) {
        super(null,new Position(0,0),vision_radius, nom,pv,force);
        MAX_EXP=10;
        CURRENT_EXP=0;
        //TODO rework les nulls
    }

    public void updateEtage(Etage etage, Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    public void addExp(int exp){
        CURRENT_EXP += exp;
        if(CURRENT_EXP>=MAX_EXP){
            MAX_EXP *= 2;
            CURRENT_EXP = 0;
            lvl++;
            TourManager.addMessage("Vous avez gagner un niveau");
        }
    }

    public int getMAX_EXP() {
        return MAX_EXP;
    }

    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    public void moveLeft() {
        move(getPosition().somme(-1,0));
    }

    public void moveRight() {
        move(getPosition().somme(1,0));
    }

    public void moveUp() {
        move(getPosition().somme(0,-1));
    }

    public void moveDown() {
        move(getPosition().somme(0,1));
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83E\uDD13";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"@";
        }
    }

}
