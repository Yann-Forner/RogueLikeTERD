package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Main;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;


public class Volcano extends AbstractMonster {

    public Volcano(Etage m, Position pos,String nom, int pv, int force, int vision_radius , int agro, int update_rate, int path_type) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, path_type);
    }

    @Override
    protected Position nextPosition() {
        return getPosition();
    }

    @Override
    public boolean updatePV(int pv) {
        BasicPlayer player = Main.getPlayer();

        if (getPosition().Distance(player.getPosition()) < 4)
        {
            player.updatePV(- getForce());
        }

        return super.updatePV(pv);
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
