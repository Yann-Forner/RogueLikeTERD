package Model.Entitys.Items.Potions;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

/**
 * Potion dont le but est d'octroyer une invincibilité temporaire au joueur
 * @author JP
 */
public class InvulPotion extends AbstractPotion {

    /**
     * Constructeur de la potion d'invincibilité
     *
     * @param e Etage où se situe la potion
     * @param nom Nom de la potion
     * @param pos Position de la potion
     * @author JP
     */
    public InvulPotion(Etage e, Position pos, String nom) {
        super(e, pos, nom);
        //TODO tu asvais push avec une errue #JP c'etait super(e, pos, nom,isOnInventory);
    }

    /**
     * Utilise la potion
     *
     * @param player Joueur utilisant la potion
     * @author JP
     */
    @Override
    public void useItem(BasicPlayer player) {

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83D\uDC89";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"H";
        }
    }

    @Override
    public String toStringWithoutColor() {
        return "H";
    }
}
