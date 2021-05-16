package Model.Entitys.Items;

import Model.Entitys.Entity;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

//TODO cette classe est une vrai poubelle #GILLIAN #JP
/**
 * Un Item est une entité qui ne se deplace pas et n'a pas de point de vie.
 * @author JP, Quentin, Gillian
 */
public abstract class AbstractItem extends Entity {
    private int prix;

    /**
     * Crée un item avec un prix.
     * @param etage Etage
     * @param position Position
     * @param nom Nom
     * @author JP
     */

    public AbstractItem(Etage etage, Position position, String nom) {
        this(etage, position, nom,0);
    }

    /**
     * Crée un item avec un prix
     * @param etage Etage de l'item
     * @param position Position de l'item dans l'etage
     * @param nom Nom de l'item
     * @author Gillian
     */
    public AbstractItem(Etage etage, Position position, String nom, int prix) {
        super(etage, position, nom);
        this.prix = prix;
    }

    /**
     * Permet de réagir en cas de contact avec un objet : tente d'acheter s'il a un prix, ramasse sinon.
     * @param e L'entité qui rentre en contact avec this.
     * @author Quentin
     */
    @Override
    public void onContact(Entity e) {
        TourManager.addNbrObjetsTotal();
    }

    /**
     * Renvoie le prix de l'item.
     * @return prix
     * @author Gillian
     */
    public int getPrix() {
        return prix;
    }

     /** 
     * Defini le comportement de l'item lorsque le joueur l'utilise.
     * @param player Joueur
     * @author JP
     */
    public abstract void useItem(Player player);

    @Override
    public String toString() {
        return Affichage.UNDERLINE;
    }
}
