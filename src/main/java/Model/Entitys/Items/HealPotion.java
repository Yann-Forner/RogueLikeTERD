package Model.Entitys.Items;

import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

public class HealPotion extends AbstractItem {

    private int healRate;

    public HealPotion(Etage e, Position pos, String nom, boolean isOnInventory, int healRate) {
        super(e, pos, nom, isOnInventory);
        this.healRate = healRate;
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.updatePV(healRate);
    }

    @Override
    public String toString() {
        return "H";
    }
}
