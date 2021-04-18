package Model.Entitys.Items;

import Model.Entitys.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
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
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83C\uDF7A";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"H";
        }
    }
}
