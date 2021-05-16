package Model.Entitys.Items.Potions;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Entity;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Concept abstrait des potions, contenant le processus de ramassage (dans onContact)
 * @author JP
 */
public abstract class AbstractPotion extends AbstractItem {
    /**
     * Constructeur de la potion
     *
     * @param etage Etage où se situe la potion
     * @param position Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @author JP
     */
    public AbstractPotion(Etage etage, Position position, String nom, int prix) {
        super(etage, position, nom, prix);
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        player.getInventory().getPotions().remove(this);
        TourManager.addMessage(Affichage.BRIGTH_PURPLE+Affichage.BOLD+"La potion a été utilisée.");
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof Player){
            super.onContact(e);
            Player player = (Player) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé "+ Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
            if(player.getInventory().addPotion(this)){
                player.getEtage().removeItem(this);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + Affichage.PURPLE;
    }

}
