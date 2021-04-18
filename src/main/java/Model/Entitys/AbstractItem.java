package Model.Entitys;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Start;

public abstract class AbstractItem extends Entity {

    private boolean isOnInventory;

    public AbstractItem(Etage etage, Position position, String nom, boolean isOnInventory) {
        super(etage, position, nom);
        this.isOnInventory = isOnInventory;
    }

    public void pickupItem(){
        BasicPlayer player = Start.getPlayer();
        player.getInventory().addItem(this);
        player.getEtage().removeItem(this);
    }

    public abstract void useItem(BasicPlayer player);

    public abstract String toString();
}
