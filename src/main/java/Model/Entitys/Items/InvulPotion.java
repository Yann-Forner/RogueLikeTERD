package Model.Entitys.Items;

import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

import javax.swing.*;

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
}
