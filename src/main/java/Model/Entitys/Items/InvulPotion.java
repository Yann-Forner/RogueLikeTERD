package Model.Entitys.Items;

import Model.Entitys.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import org.json.JSONObject;

public class InvulPotion extends AbstractItem {

    public InvulPotion(Etage e, Position pos, String nom, boolean isOnInventory) {
        super(e, pos, nom, isOnInventory);
    }

    @Override
    public void useItem(BasicPlayer player) {

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC89";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"H";
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("AbstractItem",super.toJSON());
        json.put("ItemType","InvulPotion");
        return json;
    }
}
