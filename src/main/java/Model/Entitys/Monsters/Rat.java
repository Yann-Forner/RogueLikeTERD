package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Rat extends AbstractMonster{
    public Rat(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC00";
        }
        else{
            return Affichage.RED+Affichage.BOLD+"M";
        }
    }
}
