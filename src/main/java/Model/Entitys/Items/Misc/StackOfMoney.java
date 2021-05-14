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
    private int montant;

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

    @Override
    public void onContact(Entity e) {
        if (!(e instanceof AbstractMonster)) {
            Player player = (Player) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom()
                    + " a ramassé "
                    + Affichage.BRIGTH_BLUE + getNom()
                    + Affichage.BLUE + "d'une valeur de "
                    + Affichage.GREEN + getMontant() + "$");
            player.addMoney(montant);
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

    /**
     * To string avec affichage différent en fonction du montant.
     * @return string (ce qu'on doit afficher
     * @author Gillian
     */
    @Override
    public String toString() {
        if (System.getProperty("os.name").equals("Linux")) {
            if (getMontant() <= 20) {
                //return 	smiley 1;
            } else if (getMontant() <= 30) {
                //return 	smiley 2;
            } else if (getMontant() <= 40) {
                //return 	smiley 3
            } else if (getMontant() <= 50) {
                //return smiley 4
            } else {
                return "";
            }

        } else {
            return Affichage.YELLOW + "$";
        }

        return "";
    }
}


