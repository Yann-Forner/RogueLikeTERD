package Model.Entitys;

import Model.Map.Etage;
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
        ArrayList<Position> pathToPlayer = Tools.Astar(etage, getPosition(), mainPlayer.getPosition(), Tools.PATH_CROSS);
        Position nextPosition = pathToPlayer.get(1);
    }


    @Override
    public String toString() {
        return null;
    }
}
