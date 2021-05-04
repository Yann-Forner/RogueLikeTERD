package Model.Entitys.Monsters;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.TourManager;

/**
 * Boss en 3 parties.
 * @author Yann
 */
public class BigMonster  extends AbstractMonster {
    private final Arm leftArm ;
    private final Arm rightArm;

    private static class Arm extends AbstractMonster {

        protected Arm(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
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


    protected BigMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        while ((!m.get(this.getPosition().getX()-1,this.getPosition().getY()-1).isAccesible() && m.get(this.getPosition().getX()-1,this.getPosition().getY()-1).getType() != Cell.Style.CellType.NORMAL)
                && (!m.get(this.getPosition().getX()+1,this.getPosition().getY()-1).isAccesible()  && m.get(this.getPosition().getX()+1,this.getPosition().getY()-1).getType() != Cell.Style.CellType.NORMAL)){
            this.move(Procedure.getAccesibleRandomPosition(true,m));
        }
        leftArm = new Arm(m, new Position(this.getPosition().getX() - 1, this.getPosition().getY() - 1), nom, pv / 2, force / 2, vision_radius, agro, update_rate_ms, path_type, lvl);
        rightArm= new Arm(m, new Position(this.getPosition().getX() + 1, this.getPosition().getY() - 1), nom, pv / 2, force / 2, vision_radius, agro, update_rate_ms, path_type, lvl);

    }

    @Override
    public void move(Position pos) {

        if(leftArm ==null && rightArm == null)super.move(pos);
        else if( this.getEtage().get(pos).isAccesible()
               && this.getEtage().get(pos.getX()-1,pos.getY()-1).isAccesible()
                && this.getEtage().get(pos.getX()+1,pos.getY()-1).isAccesible()){
            leftArm.move(new Position(pos.getX()-1, pos.getY()-1));
            rightArm.move(new Position(pos.getX()+1, pos.getY()-1));
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
    public void updateMonster() {
        super.updateMonster();
    }

    @Override
    public String toString() {
        return super.toString() + "U";
    }
}
