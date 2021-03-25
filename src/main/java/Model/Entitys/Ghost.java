package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Ghost extends Entity {
    public Ghost(Etage m, Position pos) {
        super(m, pos);
    }

    @Override
    public void updateEntity(Etage etage, BasicPlayer mainPlayer) {
        getEtage().get(getPosition()).setEntity(null);

        ArrayList<Position> pathToPlayer = Tools.Astar(etage, getPosition(), mainPlayer.getPosition(), Tools.PATH_CROSS);
        if(pathToPlayer.size()>3){
            Position nextPosition = pathToPlayer.get(pathToPlayer.size()-2);
            setPosition(nextPosition);
            etage.get(getPosition()).setEntity(this);
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC7B";
        }
        else{
            return Affichage.PURPLE+Affichage.BOLD+"W";
        }
    }
}
