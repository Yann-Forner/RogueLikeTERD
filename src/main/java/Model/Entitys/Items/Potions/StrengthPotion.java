package Model.Entitys.Items.Potions;

import Model.Entitys.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class StrengthPotion extends AbstractPotion {

    private int buffMultiplicator;

    public StrengthPotion(Etage e, Position pos, String nom, int buffMultiplicator) {
        super(e, pos, nom);
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
