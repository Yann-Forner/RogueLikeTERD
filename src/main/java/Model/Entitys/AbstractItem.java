package Model.Entitys;

import Model.Entitys.Items.HealPotion;
import Model.Entitys.Items.ItemFactory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Map.Etage_Strategy.CircleEtageStrategy;
import Model.Map.Etage_Strategy.DonjonStrategy;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Etage_Strategy.NormalEtageStrategy;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.TourManager;

/**
 * Un Item est une entité qui ne se deplace pas et n'a pas de point de vie.
 *
 * @author JP, Quentin, Gillian
 */
public abstract class AbstractItem extends Entity {
    private int prix;

    /**
     * Crée un item avec un prix.
     *
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
     *
     * @param etage
     * @param position
     * @param nom
     * @Gillian
     */
    public AbstractItem(Etage etage, Position position, String nom, int prix) {
        super(etage, position, nom);
        this.prix = prix;

     * Crée un item.
     * @param etage Etage
     * @param position Position
     * @param nom Nom
     * @author JP
     */
    public AbstractItem(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }

    /**
     * Permet de réagir en cas de contact avec un objet : tente d'acheter s'il a un prix, ramasse sinon
     *
     * @param e
     * @author Gillian, JP
     */
    @Override
    public void onContact(Entity e) {
        //TODO remplacer le instanceof #JP
        if (!(e instanceof AbstractMonster)) {
            BasicPlayer player = (BasicPlayer) e;
            if (prix == 0) {
                TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé " + Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
                player.getInventory().addItem(this);
                player.getEtage().removeItem(this);
            } else {
                if (player.removeMoney(getPrix())) {
                    TourManager.addMessage(Affichage.BLUE + player.getNom()
                            + " a acheté "
                            + Affichage.BRIGTH_BLUE + getNom()
                            + Affichage.BLUE + "pour la somme de"
                            + Affichage.GREEN + getPrix()
                            + Affichage.GREEN + "$"

                    );
                    player.getInventory().addItem(this);
                    player.getEtage().removeItem(this);
                } else {
                    TourManager.addMessage(Affichage.BLUE + player.getNom()
                            + Affichage.RED + "ne peut pas acheter"
                            + Affichage.BRIGTH_BLUE + getNom()
                            + Affichage.BLUE + "pour la somme de"
                            + Affichage.GREEN + getPrix()
                            + Affichage.GREEN + "$"
                    );
                }
            }
        }
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
    public abstract void useItem(BasicPlayer player);

}
