package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;

/**
 * Zombie, se déplace lentement
 * @auhtor Quentin
 */
public class Zombie extends AbstractMonster {
    public Zombie(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83E\uDDDF";
        }
        else{
            return super.toString() + "Z";
        }
    }
}
