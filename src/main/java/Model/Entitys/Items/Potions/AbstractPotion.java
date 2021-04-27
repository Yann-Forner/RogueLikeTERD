package Model.Entitys.Items.Potions;

import Model.Entitys.AbstractItem;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public class AbstractPotion extends AbstractItem {
    public AbstractPotion(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }

    @Override
    public void useItem(BasicPlayer player) {

    }

    @Override
    public void onContact(Entity e) {
        if(!(e instanceof AbstractMonster)){
            BasicPlayer player = (BasicPlayer) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé "+ Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
            player.getInventory().addPotion(this);
            player.getEtage().removeItem(this);
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
