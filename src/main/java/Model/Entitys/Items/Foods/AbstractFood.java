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
    protected final FoodFactory.FoodType foodType;
    /**
     * Constructeur de la nourriture
     * @param etage Etage de la nourriture
     * @param position Position de la nourriture
     * @param foodType Quantité de pv restoré en %
     */
    public AbstractFood(Etage etage, Position position, FoodFactory.FoodType foodType) {
        super(etage, position, foodType.getNom());
        this.foodType = foodType;
    }

    @Override
    public void useItem(Player player) {
        double pvMax = player.getMAX_PV();
        double healConverted = pvMax / 100 * foodType.getHeal();
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
        if(System.getProperty("os.name").equals("Linux")){
            return foodType.getForme_linux();
        }
        else{
            return super.toString() + Affichage.RED + foodType.getForme_windows();
        }
    }

}
