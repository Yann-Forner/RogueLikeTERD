package Model.Entitys.Monsters;

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
        //TODO eviter qu'il se bloque dans un objet
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
            return super.toString() + "U";
        }
    }
}
