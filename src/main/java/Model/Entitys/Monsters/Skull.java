package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Utils.Start;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public class Skull extends AbstractMonster {

    public Skull(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public void updateMonster() {
        BasicPlayer player = Start.getPlayer();
        double distance = player.getPosition().Distance(getPosition());
        if(Alert==0 && distance<getVision_radius()){
            Alert=1;
            TourManager.addMessage(getNom()+" vous a reperé!!!");
        }
        if(distance<getVision_radius()/4){
            Position[] positions = getPosition().voisins(getEtage()).stream().filter(x -> getEtage().get(x).isAccesible()).toArray(Position[]::new);
            Position plus_loin = positions.length>0 ? positions[0] : null;
            for (Position p : positions){
                if(player.getPosition().Distance(p)>player.getPosition().Distance(plus_loin)){
                    plus_loin=p;
                }
            }
            move(plus_loin);
        }
        else if(distance>=getVision_radius()/4 && distance<getVision_radius()/3){
            Affichage.Projectile(getEtage(),getPosition(),player.getPosition(),new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_RED,"\uD83D\uDD25"));
            player.updatePV(- getForce());
        }
        else if(distance>=getVision_radius()/3 && distance < (Alert==0 ? getVision_radius() : Agro)){
            move(nextPosition());
        }
        else if(Alert==1 && distance<getVision_radius()){
            Alert=0;
        }
        else{
            Alert=0;
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC80";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"S";
        }
    }
}
