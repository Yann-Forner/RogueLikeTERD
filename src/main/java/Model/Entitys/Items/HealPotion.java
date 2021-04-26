package Model.Entitys.Items;

import Model.Entitys.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class HealPotion extends AbstractItem {

    private int healRate;

    public HealPotion(Etage e, Position pos, String nom, boolean isOnInventory, int healRate) {
        super(e, pos, nom);
        this.healRate = healRate;
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.updatePV(healRate);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83C\uDF7C";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"H";
        }
    }
}
