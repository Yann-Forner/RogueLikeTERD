package Model.Entitys.Items.Foods;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.Objects;

/**
 * Concept abstrait des nourriture. C'est ici que sera gérer le fait de manger l'item.
 * @author JP
 */
public abstract class AbstractFood extends AbstractItem {

    private final int heal;
    /**
     * Constructeur de la nourriture
     * @param etage Etage de la nourriture
     * @param position Position de la nourriture
     * @param nom Nom de la nourriture
     * @param heal Quantité de pv restoré en %
     */
    public AbstractFood(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom);
        this.heal = heal;
    }

    @Override
    public void useItem(Player player) {
        double pvMax = player.getMAX_PV();
        double healConverted = pvMax / 100 * heal;
        TourManager.addMessage("soin: "+healConverted);
        player.updatePV((int) healConverted,true);
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof Player) {
            super.onContact(e);
            useItem(Objects.requireNonNull(Start.getPlayer()));
            getEtage().removeItem(this);
            Sound.playAudio(Sound.Sons.MANGER,0);
        }
    }

    @Override
    public String toString() {
        return super.toString() + Affichage.RED;
    }

}
