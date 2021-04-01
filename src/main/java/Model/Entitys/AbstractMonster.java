package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Main;
import Model.Utils.Position;
import Model.Utils.Tools;

import java.util.ArrayList;

public abstract class AbstractMonster extends Entity{
    private int Alert=0;
    protected final int Agro;
    protected int pathCross;

    protected AbstractMonster(Etage m, Position pos, int vision_radius, int agro) {
        super(m, pos);
        setVision_radius(vision_radius);
        this.Agro=agro;
    }

    public void updateMonster(Etage etage, BasicPlayer mainPlayer){
        int vision_radius = Alert>0 ? getVision_radius()*2 : getVision_radius();
        if(mainPlayer.getPosition().Distance(getPosition())<=vision_radius){
            if(Alert==0){
                Main.addMessage("Un monstre vous a reperé!!!");
            }
            Alert=Agro;
            etage.get(getPosition()).setEntity(null);
            ArrayList<Position> pathToPlayer = Tools.Astar(etage, getPosition(), mainPlayer.getPosition(), pathCross);
            setPosition(pathToPlayer.get(pathToPlayer.size()-2));
            etage.get(getPosition()).setEntity(this);
            Alert--;
        }
        else{
            Alert=0;
        }
    }

    @Override
    public String toString() {
        return Affichage.RED+'N';
    }
}
