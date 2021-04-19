package Model.Entitys.Monsters;

import Model.Entitys.AbstractAlive;
import Model.Utils.Start;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;

public abstract class AbstractMonster extends AbstractAlive {
    protected int Alert=0;
    protected final int Agro;
    private final int pathtype;
    private final int update_rate_ms;

    protected AbstractMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
        super(m, pos, vision_radius, nom, pv, force, lvl);
        this.update_rate_ms=update_rate_ms;
        this.Agro=agro;
        this.pathtype=path_type;
        TourManager.addMonsterSchedule(this);
    }

    public void updateMonster() {
        double vision_radius = Alert>0 ? Agro : getVision_radius();
        if(Start.getPlayer().getPosition().Distance(getPosition())<=vision_radius){
            if(Alert==0){
                TourManager.addMessage(getNom()+" vous a reperé!!!");
            }
            Alert=Agro;
            move(nextPosition());
            Alert--;
        }
        else{
            Alert=0;
        }
    }

    protected Position nextPosition(){
        ArrayList<Position> pathToPlayer = Tools.Astar(getEtage(), getPosition(), Start.getPlayer().getPosition(), pathtype);
        return pathToPlayer.size()==0 ? null : pathToPlayer.get(pathToPlayer.size() - 2);
    }

    @Override
    public void death() {
        TourManager.addMessage(getNom() + Affichage.BRIGTH_RED + " est mort.");
        getEtage().removeMonster(this);
        Start.getPlayer().addExp(getExp());
    }

    public int getUpdate_rate_ms() {
        return update_rate_ms;
    }

    @Override
    public void updatePVMessage() {
        TourManager.addMessage(getNom() + Affichage.YELLOW + " n'a plus que " + getPv() + "pv.");
    }

    @Override
    public String getNom() {
        return Affichage.GREEN + super.getNom() + Affichage.BRIGTH_GREEN + Affichage.BOLD + "[" + lvl + "]" + Affichage.RESET;
    }

    @Override
    public String toString() {
        return Affichage.RED+'N';
    }
}
