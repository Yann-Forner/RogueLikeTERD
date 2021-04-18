package Model.Entitys.Items;

import Model.Entitys.AbstractEntity;
import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

public abstract class AbstractItem extends AbstractEntity  {

    private boolean isOnInventory;

    public AbstractItem(Etage etage, Position position, String nom, boolean isOnInventory) {
        super(etage, position, nom);
        this.isOnInventory = isOnInventory;
    }

    public abstract void useItem(BasicPlayer player);

    public abstract String toString();
}
