package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Potion de puissance, le but est d'augmenter temporairement les dégats du joueur
 * @author JP
 */
public class StrengthPotion extends AbstractPotion {

    private final double buffMultiplicator;
    private final int seconds;

    /**
     * Constructeur de la potion de force.
     *
     * @param e Etage ou se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @param buffMultiplicator Multiplicateur de force de la potion
     * @param seconds Durée d'effet de la potion
     * @author JP
     */
    public StrengthPotion(Etage e, Position pos, String nom, int prix, double buffMultiplicator, int seconds) {
        super(e, pos, nom, prix);
        this.buffMultiplicator = buffMultiplicator;
        this.seconds = seconds;
    }

    /**
     * Utilise la potion
     *
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        if(player.isBuffed()) {
            TourManager.addMessage("Une potion de force est déjà en train d'être consommée !");
        }
        else {
            super.useItem(player);
            player.buffStrength(buffMultiplicator, seconds);
        }

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83C\uDF7A";
        }
        else{
            return super.toString()+"s";
        }
    }
}
