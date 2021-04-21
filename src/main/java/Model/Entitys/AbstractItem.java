package Model.Entitys;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;
import org.json.JSONObject;

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
            TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé "+ Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
            player.getInventory().addItem(this);
            player.getEtage().removeItem(this);
        }
    }

    public abstract void useItem(BasicPlayer player);

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("Entity",super.toJSON());
        json.put("isOnInventory",isOnInventory);
        return json;
    }
}
