package Model.Entitys.Items.Misc;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Classe décrivant un tas d'argent ramassable par le joueur
 * @author Gillian
 */
public class StackOfMoney extends AbstractItem {
    private final int montant;

    /**
     * Constructeur d'un stack d'argent
     * @param e Etage
     * @param pos Posisiton
     * @param montant Montant
     * @author Gillian
     */
    public StackOfMoney(Etage e, Position pos, int montant) {
        super(e, pos, "Argent");
        this.montant = montant;
    }

    @Override
    public void useItem(Player player) { }

    /**
     * Ajoute l'argent au joueur
     * @param e L'entité qui rentre en contact avec this.
     * @author Gillian
     */
    @Override
    public void onContact(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom()
                    + " a ramassé "
                    + Affichage.BRIGTH_BLUE + getNom()
                    + Affichage.BLUE + " d'une valeur de "
                    + Affichage.GREEN + getMontant() + "$");
            player.getInventory().addMoney(montant);
            player.getEtage().removeItem(this);
        }
    }

    /**
     * Récupération du montant
     * @return montant
     * @author Gillian
     */
    public int getMontant() {
        return montant;
    }

    @Override
    public String getNom() {
        if (getMontant() <= 25) {
            return "Pièce d'or";
        }
        else if (getMontant() <= 50) {
            return "Billet simple";
        }
        else if (getMontant() <= 75) {
            return "Liasse de billet";
        }
        else{
            return "Sac d'or";
        }
    }

    @Override
    public String toString() {
        if (System.getProperty("os.name").equals("Linux")) {
            if (getMontant() <= 25) {
                return "\uD83D\uDCB5";
            }
            else if (getMontant() <= 50) {
                return "\uD83D\uDCB8";
            }
            else if (getMontant() <= 75) {
                return "\uD83D\uDCB4";
            }
            else{
                return "\uD83D\uDCB0";
            }
        } else {
            return Affichage.YELLOW + "$";
        }
    }
}


