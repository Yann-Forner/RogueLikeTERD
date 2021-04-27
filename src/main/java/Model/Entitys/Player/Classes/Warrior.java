package Model.Entitys.Player.Classes;

import Model.Entitys.Player.BasicPlayer;
import Model.Utils.Affichage;

/**
 * Le guerrier est celui qui as le plus de points de vie mais le moins d'attaque.
 * @author Quentin
 */
public class Warrior extends AbstractClass {

    @Override
    public int getVisionRadius() {
        return 100;
    }

    @Override
    public int getBasePV() {
        return 100;
    }

    @Override
    public int getBaseForce() {
        return 3;
    }

    @Override
    public int getSpeed() {
        return 100;
    }

    @Override
    public void setBaseItems(BasicPlayer player) {

    }

    @Override
    public String getNom() {
        return "Le Guerrier";
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "👹";
        }
        else{
            return Affichage.RED+Affichage.BOLD+"@";
        }
    }
}
