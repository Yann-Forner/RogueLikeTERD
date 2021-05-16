package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion de puissance, le but est d'augmenter temporairement les dégats du joueur
 * @author JP
 */
public class StrengthPotion extends AbstractPotion {
    private final double buffMultiplicator;

    /**
     * Constructeur de la potion de force.
     *
     * @param e Etage ou se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @param buffMultiplicator Multiplicateur de force de la potion
     * @author JP
     */
    public StrengthPotion(Etage e, Position pos, String nom, int prix, double buffMultiplicator) {
        super(e, pos, nom, prix);
        this.buffMultiplicator = buffMultiplicator;
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        if(player.getBuff(Buffs.FORCE)) {
            TourManager.addMessage("Une potion de force est déjà en train d'être consommée !");
        }
        else {
            super.useItem(player);
            player.setBuff(Buffs.FORCE,true);
            int forceConverted = (int) (((double) player.getForce()) * (1.0 + buffMultiplicator / 100.0));
            player.setForce(forceConverted);
            TourManager.addMessage(Affichage.BRIGTH_BLUE + "Debut augmentation force");
            TourManager.getExecutor().schedule(() -> {
                player.setForce(player.getMAX_FORCE());
                player.setBuff(Buffs.FORCE,false);
                TourManager.addMessage(Affichage.BRIGTH_BLUE + "Fin augmentation force");
            }, 5, TimeUnit.SECONDS);
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
