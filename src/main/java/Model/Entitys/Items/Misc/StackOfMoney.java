package Model.Entitys.Items.Misc;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
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

    /**
     * Champ indiquant le montant du stack d'argent
     * @author Gillian
     */
    private final int montant;

    /**
     * Constructeur d'un stack d'argent
     * @param e Etage
     * @param pos Posisiton
     * @param nom Nom
     * @param montant Montant
     * @author Gillian
     */
    public StackOfMoney(Etage e, Position pos, String nom, int montant) {
        super(e, pos, nom);
        this.montant = montant;
    }

    /**
     * Pas besoin de redéfinir la méthode car on ne peut pas utiliser un stack d'argent (disparait au ramassage)
     * @param player Joueur
     */
    @Override
    public void useItem(Player player) {

    }

    //TODO ajouter l'argent au joueur

    /**
     * Ajoute l'argent au joueur
     * @param e L'entité qui rentre en contact avec this.
     *
     * @author Gillian
     */
    @Override
    public void onContact(Entity e) {
        if (!(e instanceof AbstractMonster)) {
            Player player = (Player) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom()
                    + " a ramassé "
                    + Affichage.BRIGTH_BLUE + getNom()
                    + Affichage.BLUE + "d'une valeur de "
                    + Affichage.GREEN + getMontant() + "$");
            player.getInventory().addMoney(montant);
            player.getEtage().removeItem(this);
        }

    }

    /**
     * récupération du montant
     *
     * @return montant
     * @author Gillian
     */
    public int getMontant() {
        return montant;
    }

    @Override
    public String getNom() {
        return super.getNom();
    }

    /**
     * To string avec affichage différent en fonction du montant.
     * @return string (ce qu'on doit afficher
     * @author Gillian
     */



    @Override
    public String toString() {
        if (System.getProperty("os.name").equals("Linux")) {
            if (getMontant() <= 25) {
                return "\uD83D\uDCB5";
            } else if (getMontant() <= 50) {
                return "\uD83D\uDCB8";
            } else if (getMontant() <= 75) {
                return "\uD83D\uDCB4";
            } else if (getMontant() <= 100) {
                return "\uD83D\uDCB0";
            } else {
                return "";
            }
        } else {
            return Affichage.YELLOW + "$";
        }
    }
}


