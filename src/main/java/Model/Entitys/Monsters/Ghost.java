package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;


public class Ghost extends AbstractMonster {
    public Ghost(Etage m, Position pos, int pv, int force, int vision_radius , int agro, int update_rate, int path_type) {
        super(m, pos, pv, force, vision_radius, agro, update_rate, path_type);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC7B";
        }
        else{
            return Affichage.GREY+Affichage.BOLD+"H";
        }
    }
}
