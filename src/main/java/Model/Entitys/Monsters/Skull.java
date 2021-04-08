package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Main;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Skull extends AbstractMonster {

    public Skull(Etage m, Position pos, String nom, int pv, int force, int vision_radius , int agro, int update_rate, int pathCross) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross);
    }

    @Override
    public void updateMonster() {
        BasicPlayer player = Main.getPlayer();
        double distance = player.getPosition().Distance(getPosition());
        if(distance<5){
            Position[] positions = getPosition().voisins(getEtage()).stream().filter(x -> getEtage().get(x).isAccesible()).toArray(Position[]::new);
            if(positions.length>0){
                Position plus_loin = positions[0];
                for (Position p : positions){
                    if(player.getPosition().Distance(p)>player.getPosition().Distance(plus_loin)){
                        plus_loin=p;
                    }
                }
                move(plus_loin);
            }
        }
        else if(distance>=5 && distance<10){
            player.updatePV(- getForce());
        }
        else if(distance>=10 && distance<20){
            move(nextPosition());
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
