package Model.Entitys.Items.Potions;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion dont le but est d'octroyer une endurance illimitée temporaire au joueur
 * @author JP
 */
public class StaminaPotion extends AbstractPotion {

    private int seconds;

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
    public void useItem(BasicPlayer player) {
        super.useItem(player);

        int originalEndurence = player.getEndurence();
        TourManager.addMessage("Pendant " + seconds + "s, votre endurance sera infinie.");

        player.setEndurence(player.getMAX_ENDURENCE() * 1000);

        TourManager.getExecutor().schedule(() -> {
            player.setEndurence(originalEndurence);
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\u2615";
        }
        else{
            return super.toString()+"e";
        }
    }
}
