package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Main;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Skull extends AbstractMonster {

    public Skull(Etage m, Position pos, String nom, int pv, int force, int vision_radius , int agro, int update_rate, int pathCross) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross);
    }

    @Override
    public boolean updatePV(int pv) {
        BasicPlayer player = Main.getPlayer();

        double distance = getPosition().Distance(player.getPosition());

        if (distance < 4 && distance > 1)
        {
            player.updatePV(- getForce());
        }

        return super.updatePV(pv);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83C\uDFF9";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"S";
        }
    }
}
