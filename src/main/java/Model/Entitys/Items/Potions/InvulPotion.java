package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion dont le but est d'octroyer une invincibilité temporaire au joueur
 * @author JP
 */
public class InvulPotion extends AbstractPotion {

    /**
     * Constructeur de la potion d'invincibilité
     * @param e Etage où se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @author JP
     */
    public InvulPotion(Etage e, Position pos, String nom, int prix) {
        super(e, pos, nom, prix);
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        if(player.getBuff(Buffs.INVINCIBLE)) {
            TourManager.addMessage("Vous êtes déjà immortel !");
        }
        else {
            TourManager.addMessage(Affichage.BRIGTH_BLUE +  "Debut invulnerabilité");
            player.setBuff(Buffs.INVINCIBLE,true);
            super.useItem(player);
            TourManager.getExecutor().schedule(() -> {
                player.setBuff(Buffs.INVINCIBLE,true);
                TourManager.addMessage(Affichage.BRIGTH_BLUE + "Fin invulnerabilité");
                }, 5, TimeUnit.SECONDS);
        }

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
