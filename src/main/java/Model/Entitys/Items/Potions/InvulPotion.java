package Model.Entitys.Items.Potions;

import Model.Entitys.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

/**
 * Potion dont le but est d'octroyer une invincibilité temporaire au joueur
 * @author JP
 */
public class InvulPotion extends AbstractPotion {

    public InvulPotion(Etage e, Position pos, String nom) {
        super(e, pos, nom);
        //TODO tu asvais push avec une errue #JP c'etait super(e, pos, nom,isOnInventory);
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
