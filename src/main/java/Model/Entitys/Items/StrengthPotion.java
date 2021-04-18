package Model.Entitys.Items;

import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

public class StrengthPotion extends AbstractItem {

    private int buffMultiplicator;

    public StrengthPotion(Etage e, Position pos, String nom, boolean isOnInventory, int buffMultiplicator) {
        super(e, pos, nom, isOnInventory);
        this.buffMultiplicator = buffMultiplicator;
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.setForce(player.getForce() * buffMultiplicator);
    }

    @Override
    public String toString() {
        return "S";
    }
}
