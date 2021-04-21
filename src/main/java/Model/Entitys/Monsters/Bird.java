package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import org.json.JSONObject;

public class Bird extends AbstractMonster {

    private boolean totop = true;

    public Bird(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    protected Position nextPosition() {
        Position haut = getPosition().somme(-1,-1);
        Position bas = getPosition().somme(+1,+1);
        if(totop) {
            if(getEtage().get(haut).isAccesible()){
                return haut;
            }
            totop=false;
            return bas;
        }
        if(getEtage().get(bas).isAccesible()){
            return bas;
        }
        totop=true;
        return haut;

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC26";
        }
        else{
            return Affichage.BLUE+Affichage.BOLD+"ù";
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("AbstractMonster",super.toJSON());
        json.put("MonsterType","Bird");
        json.put("totop",totop);
        return json;
    }
}
