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
        super(etage, position, nom);
        prix = 0;

    }

    /**
     * Crée un item avec un prix
     * @param etage
     * @param position
     * @param nom
     * @Gillian
     */
    public AbstractItem(Etage etage, Position position, String nom, int prix) {
        super(etage, position, nom);
        this.prix = prix;
    }

    /**
     * Permet de réagir en cas de contact avec un objet : tente d'acheter s'il a un prix, ramasse sinon
     * @param e
     * @author Gillian, JP, Quentin
     */
    @Override
    public void onContact(Entity e) {
        TourManager.addNbrObjetsTotal();
    }

    /**
     * Renvoie le prix de l'objet
     * @return prix
     * @author Gillian
     */
    public int getPrix() {
        return prix;
    }


    /**
     * Renvoit un item aléatoire
     * @return Item
     * @author Gillian
    /*
    public static AbstractItem getRandomItem(){
        switch (Procedure.getRandomInt(11,0)){
            case 0, 1, 2 -> {
                return new ;
            }
            case 3, 4, 5  -> {
                return new ;
            }
            case 6, 7, 8 -> {
                return new  ;
            }
            case 9,10,11 -> {
                return new ItemFactory().get
            }
            default -> {
                return new CircleEtageStrategy();
            }
        }
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
