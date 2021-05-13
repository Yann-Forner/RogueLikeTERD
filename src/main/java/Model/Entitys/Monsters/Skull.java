package Model.Entitys.Monsters;

import Model.Entitys.Player.Player;
import Model.Utils.*;
import Model.Map.Cell;
import Model.Map.Etage;

import java.util.Objects;

/**
 * Skull, attaque le joueur en lui tirant des projectiles
 * @author Quentin
 */
public class Skull extends AbstractMonster {

    public Skull(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public void updateMonster() {
        Player player = Start.getPlayer();
        double distance = Objects.requireNonNull(player).getPosition().Distance(getPosition());
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
            Affichage.Projectile(getEtage(),Tools.getLigne(player.getPosition(),getPosition()),new Cell.Style(Cell.Style.CellType.PROJECTILE));
            player.updatePV(- getForce(),false);
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
            return super.toString() + "S";
        }
    }
}
