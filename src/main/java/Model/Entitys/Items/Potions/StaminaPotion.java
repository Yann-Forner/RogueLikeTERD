package Model.Entitys.Items.Potions;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

import java.util.concurrent.TimeUnit;

/**
 * Potion dont le but est d'octroyer une endurance illimitée temporaire au joueur
 * @author JP
 */
public class StaminaPotion extends AbstractPotion {

    /**
     * Constructeur de la potion d'endurance
     * @param e Etage où se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param prix Prix de la potion
     * @author JP
     */
    public StaminaPotion(Etage e, Position pos, String nom, int prix) {
        super(e, pos, nom, prix);
    }

    /**
     * Utilise la potion
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(Player player) {
        if(player.getBuff(Buffs.ENERGIE)) {
            TourManager.addMessage("Une potion d'endurance est déjà en train d'être consommée !");
        }
        else {
            TourManager.addMessage(Affichage.BRIGTH_BLUE + "Debut endurence infinie");
            player.setBuff(Buffs.ENERGIE,true);
            super.useItem(player);
            TourManager.getExecutor().schedule(() -> {
                player.setBuff(Buffs.ENERGIE,false);
                TourManager.addMessage(Affichage.BRIGTH_BLUE + "Fin endurence infinie");
                }, 5, TimeUnit.SECONDS);
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
