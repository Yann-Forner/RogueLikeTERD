package Model.Entitys.Items.Foods;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

/**
 * Concept abstrait des nourriture. C'est ici que sera gérer le fait de manger l'item.
 * @author JP
 */
public abstract class AbstractFood extends AbstractItem {

    private int heal;
    /**
     * Constructeur de la nourriture
     * @param etage Etage de la nourriture
     * @param position Position de la nourriture
     * @param nom Nom de la nourriture
     * @param heal
     */
    public AbstractFood(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom);
        this.heal = heal;
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.updatePV(heal);
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof BasicPlayer) {
            super.onContact(e);
            BasicPlayer player = ((BasicPlayer) e);
            player.updatePV(Math.min(heal, player.getMAX_PV() - player.getPv()));
            getEtage().removeItem(this);
        }
    }

    public int getHeal() {
        return heal;
    }

    @Override
    public String toString() {
        return super.toString() + Affichage.RED;
    }

}
