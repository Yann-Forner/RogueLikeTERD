package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Zombie extends AbstractMonster {
    public Zombie(Etage m, Position pos, int pv, int force, int vision_radius , int agro, int update_rate, int pathCross) {
        super(m, pos, pv, force, vision_radius, agro, update_rate, pathCross);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83E\uDDDF";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"Z";
        }
    }
}
