package Model.Entitys;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Un Item est une entité qui ne se deplace pas et n'a pas de point de vie.
 * @author JP, Quentin
 */
public abstract class AbstractItem extends Entity {

    /**
     * Crée un item.
     * @param etage Etage
     * @param position Position
     * @param nom Nom
     * @author JP
     */
    public AbstractItem(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }

    @Override
    public void onContact(Entity e) {

    }

    /**
     * Defini le comportement de l'item lorsque le joueur l'utilise.
     * @param player Joueur
     * @author JP
     */
    public abstract void useItem(BasicPlayer player);

}
