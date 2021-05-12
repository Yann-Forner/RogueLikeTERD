package Model.Entitys.Monsters;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Tools;

/**
 * Escargot qui se deplace en ligne droite jusqu'a ce qu'il rencontre un obstacle.
 * @author Quentin
 */
public class Snail extends AbstractMonster {
    private boolean toleft = true;
    public Snail(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }



    @Override
    protected Position nextPosition() {
        Position gauche = getPosition().somme(-1,0);
        Position droite = getPosition().somme(1,0);
        Position next;
        if(toleft) {
            if(getEtage().get(gauche).isAccesible()){
                next = gauche;
            }
            else{
                next = droite;
                toleft = false;
            }
        }
        else{
            if(getEtage().get(droite).isAccesible()){
                next = droite;
            }
            else{
                next = gauche;
                toleft = true;
            }
        }
        Entity e = getEtage().get(next).getEntity();
        if(e instanceof AbstractMonster || e instanceof AbstractItem ){
            toleft = !toleft;
            return null;
        }
        return next;
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC0C";
        }
        else{
            return super.toString() + "G";
        }
    }
}
