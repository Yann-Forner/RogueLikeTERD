package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Bee extends AbstractMonster {
    private final int BasePv;

    public Bee(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
        this.BasePv=pv;
    }

    @Override
    public boolean updatePV(int pv){
        boolean isAlive = super.updatePV(pv);
        if(!isAlive){
            if(BasePv!=1){
                getEtage().addMonster(MonsterFactory.getNewMonster(getEtage(), MonsterFactory.MonsterType.BEE,BasePv/2));
                getEtage().addMonster(MonsterFactory.getNewMonster(getEtage(), MonsterFactory.MonsterType.BEE,BasePv/2));
            }
        }
        return isAlive;
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC1D";
        }
        else{
            return Affichage.YELLOW+Affichage.BOLD+"B";
        }
    }
}
