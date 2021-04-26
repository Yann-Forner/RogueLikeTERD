package Model.Entitys.Items;

import Model.Entitys.AbstractItem;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Classe décrivant un tas d'argent ramassable par le joueur
 *
 * @author Gillian
 */
public class StackOfMoney extends AbstractItem {

    /**
     * Champ indiquant le montant du stack d'argent
     *
     * @author Gillian
     */
    private int montant;

    /**
     * Constructeur d'un stack d'argent
     *
     * @param e
     * @param pos
     * @param nom
     * @param montant
     * @author Gillian
     */
    public StackOfMoney(Etage e, Position pos, String nom, int montant) {
        super(e, pos, nom);
        this.montant = montant;
    }

    /**
     * Pas besoin de redéfinir la méthode car on ne peut pas utiliser un stack d'argent (disparait au ramassage)
     *
     * @param player Joueur
     */
    @Override
    public void useItem(BasicPlayer player) {

    }


    /**
     * Récupère le tas d'argent et augmente l'argent du jouer lorsque l'on passe dessus
     *
     * @param e
     * @author Gillian
     */
    @Override
    public void onContact(Entity e) {
        if (!(e instanceof AbstractMonster)) {
            BasicPlayer player = (BasicPlayer) e;
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
     * to string avec affichage différent en fonction du montant
     *
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
            return Affichage.GREEN + Affichage.BOLD + "H";
        }

        return "";
    }
}


