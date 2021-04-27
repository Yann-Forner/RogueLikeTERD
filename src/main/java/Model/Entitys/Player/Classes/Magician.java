package Model.Entitys.Player.Classes;

import Model.Entitys.Player.BasicPlayer;
import Model.Utils.Affichage;

/**
 * Le magicien est le plus lent et peut faire des degats de zone.
 * @author Quentin
 */
public class Magician extends AbstractClass{

    @Override
    public int getVisionRadius() {
        return 100;
    }

    @Override
    public int getBasePV() {
        return 70;
    }

    @Override
    public int getBaseForce() {
        return 5;
    }

    @Override
    public int getSpeed() {
        return 200;
    }

    @Override
    public void setBaseItems(BasicPlayer player) {

    }

    @Override
    public String getNom() {
        return "Le Magicien";
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🧙";
        }
        else{
            return Affichage.BLUE+Affichage.BOLD+"@";
        }
    }
}
