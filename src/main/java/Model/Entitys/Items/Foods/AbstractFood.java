package Model.Entitys.Items.Foods;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Concept abstrait des nourriture. C'est ici que sera gérer le fait de manger l'item.
 * @author JP
 */
public class AbstractFood extends AbstractItem {

    /**
     * Constructeur de la nourriture
     * @param etage Etage de la nourriture
     * @param position Position de la nourriture
     * @param nom Nom de la nourriture
     */
    public AbstractFood(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }

    @Override
    public void useItem(BasicPlayer player) {

    }

    @Override
    public String toString() {
        return null;
    }
}
