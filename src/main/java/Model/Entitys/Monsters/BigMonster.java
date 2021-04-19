package Model.Entitys.Monsters;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class BigMonster  extends AbstractMonster {
    private final HandOfMonster leftHand;
    private final HandOfMonster rightHand;
    protected BigMonster(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        leftHand = new HandOfMonster(m, new Position(pos.getX()-1,pos.getY()-1), nom, pv/2, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        rightHand = new HandOfMonster(m, new Position(pos.getX()+1,pos.getY()-1), nom, pv/2, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        while(!leftHand.getEtage().get(pos.getX()-1,pos.getY()-1).isAccesible() && !rightHand.getEtage().get(pos.getX()+1,pos.getY()-1).isAccesible()){
            this.move(Procedure.getAccesibleRandomPosition(true,this.getEtage()));
        }
    }

    @Override
    public boolean updatePV(int pv) {
        boolean isAlive = super.updatePV(pv);
        if (!isAlive){
            leftHand.updatePV(-leftHand.getPv());
            rightHand.updatePV(-rightHand.getPv());
        }
        return isAlive;
    }

    @Override
    public void move(Position pos) {
        if(leftHand.getEtage().get(new Position(pos.getX()-1,pos.getY()-1)).isAccesible()
                && rightHand.getEtage().get(new Position(pos.getX()+1,pos.getY()-1)).isAccesible()
                && this.getEtage().get(pos).isAccesible() ){
            leftHand.move(new Position(pos.getX()-1,pos.getY()-1));
            rightHand.move(new Position(pos.getX()+1,pos.getY()-1));
            super.move(pos);
        }
    }

    @Override
    public void death() {
        super.death();
        Procedure.setRandomUPnDOWN(getEtage());
    }

    @Override
    public String toString() {
        return Affichage.BLUE_BACKGROUND+Affichage.BOLD+"o";
    }
}
