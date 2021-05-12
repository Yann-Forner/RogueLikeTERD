package Model.Entitys.Items.Foods;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Sound;

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
     * @param heal Quantité de pv restoré
     */
    public AbstractFood(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom);
        this.heal = heal;
    }

    @Override
    public void useItem(BasicPlayer player) {
        int pvMax = ((BasicPlayer) player).getMAX_PV();
        int healConverted = pvMax / 100 * getHeal();
        player.updatePV(healConverted);
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof BasicPlayer) {
            super.onContact(e);
            BasicPlayer player = ((BasicPlayer) e);
            int pvMax = ((BasicPlayer) e).getMAX_PV();
            int healConverted = pvMax / 100 * getHeal();
            player.updatePV(Math.min(healConverted, pvMax - player.getPv()));
            getEtage().removeItem(this);
            Sound.playAudio(Sound.Sons.MANGER,0);
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
