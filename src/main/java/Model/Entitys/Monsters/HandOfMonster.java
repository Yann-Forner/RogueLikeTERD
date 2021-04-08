package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class HandOfMonster extends AbstractMonster{
    protected HandOfMonster(Etage m, Position pos, String nom, int pv, int force, int vision_radius, int agro, int update_rate_ms, int path_type) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type);
    }

    @Override
    public void updateMonster() {

    }


    @Override
    public String toString() {
        return Affichage.BLUE_BACKGROUND+Affichage.BOLD+"|";
    }
}
