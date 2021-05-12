package Model.Entitys.Monsters;

import Model.Entitys.Items.AbstractItem;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Tools;

/**
 * Bird, se déplace en diagonale, ne cheche pas à attaquer le joueur
 * @author JP
 */
public class Bird extends AbstractMonster {

    private boolean totop = true;

    public Bird(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    protected Position nextPosition() {
        Position haut = getPosition().somme(-1,-1);
        Position bas = getPosition().somme(+1,+1);
        Cell cell;
        if(totop) {
            cell = getEtage().get(haut);
            if(cell.isAccesible() && !(cell.getEntity() instanceof AbstractItem)){
                return haut;
            }
            totop=false;
            return bas;
        }
        cell = getEtage().get(bas);
        if(cell.isAccesible() && !(cell.getEntity() instanceof AbstractItem)){
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
            return super.toString() + "U";
        }
    }
}
