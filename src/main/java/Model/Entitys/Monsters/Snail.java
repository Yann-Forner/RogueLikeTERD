package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Main;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;
import Model.Utils.TourManager;

import java.util.ArrayList;

public class Snail extends AbstractMonster{
    private boolean toleft = true;
    public Snail(Etage m, Position pos, String nom, int pv, int force, int vision_radius , int agro, int update_rate, int pathCross) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross);
    }

    @Override
    protected Position nextPosition() {
        Position gauche = getPosition().somme(-1,0);
        Position droite = getPosition().somme(1,0);
        if(toleft) {
            if(getEtage().get(gauche).isAccesible()){
                return gauche;
            }
            toleft=false;
            return droite;
        }
        if(getEtage().get(droite).isAccesible()){
            return droite;
        }
        toleft=true;
        return gauche;

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC0C";
        }
        else{
            return Affichage.BLUE+Affichage.BOLD+"G";
        }
    }
}
