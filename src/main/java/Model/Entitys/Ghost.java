package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;


public class Ghost extends AbstractMonster {

    public Ghost(Etage m, Position pos, int vision_radius , int agro) {
        super(m, pos, vision_radius, agro);
        pathCross = Tools.PATH_CROSS;
    }

    @Override
    public void updateMonster(Etage etage, BasicPlayer mainPlayer) {
        super.updateMonster(etage,mainPlayer);
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
