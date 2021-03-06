package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Poition dont le but est de soigner le joueur. Le taux de heal est ajustable.
 * @author JP
 */
public class HealPotion extends AbstractPotion {
    private final int healRate;

    /**
     * Constructeur de la potion de soin
     * @param e Etage où se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @param healRate Quantité de soin de la potion
     * @author JP
     */
    public HealPotion(Etage e, Position pos, String nom, int prix, int healRate) {
        super(e, pos, nom, prix);
        this.healRate = healRate;
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        super.useItem(player);
        double pvMax = player.getMAX_PV();
        double healConverted = pvMax / 100 * healRate;
        player.updatePV((int) healConverted,true);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83C\uDF7C";
        }
        else{
            return super.toString()+"h";
        }
    }
}
