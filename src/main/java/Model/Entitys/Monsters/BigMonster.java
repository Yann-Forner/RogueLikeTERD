package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Tools;
import Model.Utils.TourManager;

import java.util.Objects;

/**
 * Boss en 3 parties qui devient plus puussant des qu'il perd en bras.
 * @author Yann, Quentin
 */
public class BigMonster  extends AbstractMonster {
    private final Arm leftArm;
    private final Arm rightArm;

    private static class Arm extends AbstractMonster {

        protected Arm(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl) {
            super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
            m.addMonster(this);
        }

        @Override
        public void updateMonster() { }

        @Override
        public String toString() {
            return super.toString() + "#";
        }
    }


    protected BigMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        leftArm = new Arm(m, new Position(this.getPosition().getX() - 1, this.getPosition().getY() - 1), nom, pv / 2, force / 2, vision_radius, agro, update_rate_ms, path_type, lvl);
        rightArm = new Arm(m, new Position(this.getPosition().getX() + 1, this.getPosition().getY() - 1), nom, pv / 2, force / 2, vision_radius, agro, update_rate_ms, path_type, lvl);
    }

    @Override
    public void move(Position pos) {
        if(leftArm == null && rightArm == null){
            super.move(pos);
        }
        else if(getEtage().get(pos).isAccesible()
                && getEtage().get(pos.getX()-1,pos.getY()-1).isAccesible()
                && getEtage().get(pos.getX()+1,pos.getY()-1).isAccesible()){
            if(Objects.requireNonNull(leftArm).getPv()>0){
                leftArm.move(new Position(pos.getX()-1, pos.getY()-1));
            }
            if(rightArm.getPv()>0){
                rightArm.move(new Position(pos.getX()+1, pos.getY()-1));
            }
            super.move(pos);
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
    public int getForce() {
        return super.getForce() * (leftArm.getPv()<=0 ? 2 : 1) * (rightArm.getPv()<=0 ? 2 : 1);
    }

    @Override
    public int getPv() {
        return super.getPv() * (leftArm.getPv()<=0 ? 2 : 1) * (rightArm.getPv()<=0 ? 2 : 1);
    }

    @Override
    public void updateMonster() {
        super.updateMonster();
    }

    @Override
    public String toString() {
        System.out.println("PV: "+getPv() + "  ->  FORCE: "+getForce());
        return super.toString() + "U";
    }
}