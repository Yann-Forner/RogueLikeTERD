package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Tail  extends AbstractMonster{
    protected Tail(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
    }



    @Override
    public boolean updatePV(int pv) {
        return true;
    }

    @Override
    public void move(Position pos) {
        super.move(pos);
    }



    @Override
    public String toString() {
        return Affichage.BLUE+Affichage.BOLD+"o";
    }
}
