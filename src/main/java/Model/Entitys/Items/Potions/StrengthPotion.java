package Model.Entitys.Items.Potions;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

/**
 * Potion de puissance, le but est d'augmenter temporairement les dégats du joueur
 * @author JP
 */
public class StrengthPotion extends AbstractPotion {

    private int buffMultiplicator;

    /**
     * Constructeur de la potion de force.
     *
     * @param e Etage ou se situe la potion
     * @param pos Position de la potion
     * @param nom Nom de la potion
     * @param buffMultiplicator Multiplicateur de force de la potion
     * @author JP
     */
    public StrengthPotion(Etage e, Position pos, String nom, int buffMultiplicator) {
        super(e, pos, nom);
        this.buffMultiplicator = buffMultiplicator;
    }

    /**
     * Utilise la potion
     *
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(BasicPlayer player) {
        player.setForce(player.getForce() * buffMultiplicator);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83C\uDF7A";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"H";
        }
    }
}
