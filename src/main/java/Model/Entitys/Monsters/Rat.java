package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Rat extends AbstractMonster{
    public Rat(Etage m, Position pos, String nom, int pv, int force, int vision_radius , int agro, int update_rate, int pathCross) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross);
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
