package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion dont le but est d'octroyer une endurance illimitée temporaire au joueur
 * @author JP
 */
public class StaminaPotion extends AbstractPotion {

    private final int seconds;

    /**
     * Constructeur de la potion d'endurance
     * @param e Etage où se situe la potion
     * @param nom Nom de la potion
     * @param pos Position de la potion
     * @param seconds Durée d'effet de la potion
     * @author JP
     */
    public StaminaPotion(Etage e, Position pos, String nom, int seconds) {
        super(e, pos, nom);
        this.seconds = seconds;
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        if(player.isStimulated()) {
            TourManager.addMessage("Une potion d'endurance est déjà en train d'être consommée !");
        }
        else {
            TourManager.addMessage("Vous êtes doppé en endurance pendant " + seconds + " secondes. Profitez-en !");
            player.setStimulated(true);
            super.useItem(player);
            TourManager.getExecutor().schedule(() -> { player.setStimulated(false); TourManager.addMessage("Vous n'êtes plus doppé."); }, 5, TimeUnit.SECONDS);
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🥃";
        }
        else{
            return super.toString()+"e";
        }
    }
}
