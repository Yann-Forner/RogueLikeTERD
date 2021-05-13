package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion dont le but est d'octroyer une invincibilité temporaire au joueur
 * @author JP
 */
public class InvulPotion extends AbstractPotion {

    private final int seconds;

    /**
     * Constructeur de la potion d'invincibilité
     * @param e Etage où se situe la potion
     * @param nom Nom de la potion
     * @param pos Position de la potion
     * @param seconds Durée d'effet de la potion
     * @author JP
     */
    public InvulPotion(Etage e, Position pos, String nom, int seconds) {
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
        super.useItem(player);
        int originalHp = player.getPv();
        TourManager.addMessage("Pendant " + seconds + "s, votre vie sera infinie.");
        //TODO fait pas ça je t'en supplie
        player.setPv(player.getMAX_PV() * 1000);
        TourManager.getExecutor().schedule(() -> player.setPv(originalHp), 5, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDC89";
        }
        else{
            return super.toString()+"i";
        }
    }
}
