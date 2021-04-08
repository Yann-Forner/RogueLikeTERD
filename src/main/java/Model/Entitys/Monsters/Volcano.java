package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Main;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;


public class Volcano extends AbstractMonster {

    public Volcano(Etage m, Position pos,String nom, int pv, int force, int vision_radius , int agro, int update_rate, int path_type) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type);
    }

    @Override
    public void updateMonster() {
        BasicPlayer player = Main.getPlayer();
        if (getPosition().Distance(player.getPosition()) < getVision_radius()) {
            player.updatePV(- getForce());
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83C\uDF0B";
        }
        else{
            return Affichage.RED+Affichage.BOLD+"V";
        }
    }
}
