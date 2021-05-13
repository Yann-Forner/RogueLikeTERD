package Model.Entitys.Items.Potions;

import Model.Entitys.Player.BasicPlayer;
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
    private int seconds;

    /**
     * Constructeur de la potion de force.
     *
     * @param e Etage ou se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param buffMultiplicator Multiplicateur de force de la potion
     * @param seconds Durée d'effet de la potion
     * @author JP
     */
    public StrengthPotion(Etage e, Position pos, String nom, double buffMultiplicator, int seconds) {
        super(e, pos, nom);
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
    public void useItem(BasicPlayer player) {
        super.useItem(player);

        int originalForce = player.getForce();
        int forceConverted = (int) (((double)originalForce) * (1.0 + buffMultiplicator / 100.0));
        player.setForce(player.getForce() + forceConverted);
        TourManager.addMessage("Pendant " + seconds + "s, la force sera augmentée de " + buffMultiplicator + "%. (" + originalForce + " -> " + forceConverted + ")");

        TourManager.getExecutor().schedule(() -> {
            int previousForce = player.getForce() - forceConverted;
            player.setForce(previousForce);
            TourManager.addMessage("Retour de la force a sa valeur de base. (" + previousForce + ")");
        }, seconds, TimeUnit.SECONDS);
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
