package Model.Entitys.Monsters;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Tools;
import Model.Utils.TourManager;

/**
 * Boss en 3 parties.
 * @author Yann
 */
public class BigMonster  extends AbstractMonster {
    private final Arm leftArm ;
    private final Arm rightArm;

    private static class Arm extends AbstractMonster {

        protected Arm(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl) {
            super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
            m.addMonster(this);
        }

        @Override
        public void updateMonster() {

        }

        @Override
        public String toString() {
            return super.toString() + "#";
        }
    }


    protected BigMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        leftArm = new Arm(m,pos.somme(Player.Direction.GAUCHE.getVecteur()).somme(Player.Direction.HAUT.getVecteur()),"Bras gauche",pv,force,vision_radius,agro,update_rate_ms,path_type,lvl);
        rightArm = new Arm(m,pos.somme(Player.Direction.DROITE.getVecteur()).somme(Player.Direction.HAUT.getVecteur()),"Bras droite",pv,force,vision_radius,agro,update_rate_ms,path_type,lvl);
    }

    @Override
    public void move(Position pos) {
        Position nextLeft = pos.somme(Player.Direction.GAUCHE.getVecteur()).somme(Player.Direction.HAUT.getVecteur());
        Position nextRight = pos.somme(Player.Direction.DROITE.getVecteur()).somme(Player.Direction.HAUT.getVecteur());
        Cell cellLeft = getEtage().get(nextLeft);
        Cell cellRight = getEtage().get(nextRight);
        if(cellLeft.isAccesible() && cellRight.isAccesible()){
            super.move(pos);
            leftArm.move(nextLeft);
            rightArm.move(nextRight);
        }
    }

    @Override
    public void death() {
        leftArm.death();
        rightArm.death();
        super.death();
        TourManager.addKillBoss();
    }

    @Override
    public void updateMonster() {
        super.updateMonster();
    }

    @Override
    public String toString() {
        System.out.println("BIG: "+ "force: "+ getForce() + " pv: "+getPv());
        return super.toString() + "U";
    }
}
