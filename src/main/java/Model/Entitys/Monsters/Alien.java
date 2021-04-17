package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class Alien extends AbstractMonster{
    public Alien(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public boolean updatePV(int pv){
        boolean isAlive = super.updatePV(pv);
        if(isAlive){
            move(Procedure.getAccesibleRandomPosition(true,getEtage()));
        }
        return isAlive;
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC7D";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"&";
        }
    }
}
