package Model.Entitys.Monsters;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Misc.StackOfMoney;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

/**
 * Bee, lorsqu'elle meurs elle fais apparaitre deux abeilles avec deux fois moins de pv
 * @author Quentin
 */
public class Bee extends AbstractMonster {
    private final int BasePv;

    public Bee(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
        this.BasePv=pv;
    }

    @Override
    public boolean updatePV(int pv, boolean limited){
        boolean isAlive = super.updatePV(pv, limited);
        if(!isAlive){
            if(BasePv!=1){
                getEtage().addMonster(new Bee(getEtage(), Procedure.getAccesibleRandomPosition(true, getEtage()), "CHILD BEE",BasePv / 2, 5, 15, 20, 900, Tools.PathType.CROSS, lvl));
                getEtage().addMonster(new Bee(getEtage(), Procedure.getAccesibleRandomPosition(true, getEtage()), "CHILD BEE",BasePv / 2, 5, 15, 20, 900, Tools.PathType.CROSS, lvl));
            }
        }
        return isAlive;
    }

    @Override
    protected void dropOnDeath() {
        int rand = Procedure.getRandomInt(4, 0);
        if(rand == 1) {
            getInventory().dropItem(this, new StackOfMoney(getEtage(), getPosition(), Procedure.getRandomInt(50, 1)));
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC1D";
        }
        else{
            return super.toString() + "B";
        }
    }
}
