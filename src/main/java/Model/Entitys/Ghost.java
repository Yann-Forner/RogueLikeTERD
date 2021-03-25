package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;

import java.util.ArrayList;

public class Ghost extends Entity {
    public Ghost(Etage m, Position pos, int deplacementCooldown) {
        super(m, pos);
        setDeplacement(deplacementCooldown - 1);
        setDeplacementCooldown(getDeplacement());
    }

    @Override
    public void updateEntity(Etage etage, BasicPlayer mainPlayer) {
        getEtage().get(getPosition()).setEntity(null);

        if(getDeplacementCooldown() == 0) {
            ArrayList<Position> pathToPlayer = Tools.Astar(etage, getPosition(), mainPlayer.getPosition(), Tools.PATH_CROSS);
            Position nextPosition;

            if(pathToPlayer.size() > 1)
                nextPosition = pathToPlayer.get(pathToPlayer.size()-2);
            else
                nextPosition = getPosition();

            setPosition(nextPosition);
            setDeplacementCooldown(getDeplacement());
        }
        else{
            setDeplacementCooldown(getDeplacementCooldown() - 1);
        }

        etage.get(getPosition()).setEntity(this);
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
