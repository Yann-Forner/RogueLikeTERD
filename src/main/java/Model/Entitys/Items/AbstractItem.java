package Model.Entitys.Items;

import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Utils.Position;

public abstract class AbstractItem {

    private Position position;
    private Etage etage;
    private boolean isOnInventory;

    public AbstractItem(Etage e, Position pos, String nom, boolean isOnInventory) {
        this.isOnInventory = false;
        this.etage = e;
        this.position = pos;
    }

    public abstract void useItem(BasicPlayer player);

    public abstract String toString();
}
