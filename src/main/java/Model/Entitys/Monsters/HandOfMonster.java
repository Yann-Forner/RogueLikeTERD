package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import org.json.JSONObject;

public class HandOfMonster extends AbstractMonster {
    protected HandOfMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
    }

    @Override
    public void updateMonster() {

    }

    @Override
    public String toString() {
        return Affichage.BLUE_BACKGROUND+Affichage.BOLD+"|";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("AbstractMonster",super.toJSON());
        json.put("MonsterType","HandOfMonster");
        return json;
    }
}
