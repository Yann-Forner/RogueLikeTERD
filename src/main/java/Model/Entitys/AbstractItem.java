package Model.Entitys;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

public abstract class AbstractItem extends Entity {

    private boolean isOnInventory;

    public AbstractItem(Etage etage, Position position, String nom, boolean isOnInventory) {
        super(etage, position, nom);
        this.isOnInventory = isOnInventory;
    }

    @Override
    public void onContact(Entity e) {
        //TODO remplacer le instanceof #JP
        if(!(e instanceof AbstractMonster)){
            BasicPlayer player = (BasicPlayer) e;
            player.getInventory().addItem(this);
            player.getEtage().removeItem(this);
        }
    }

    public abstract void useItem(BasicPlayer player);

    public abstract String toString();
}
