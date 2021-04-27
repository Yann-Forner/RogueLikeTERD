package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Consommable de nourriture
 * @author JP
 */
public class Apple extends AbstractFood {
    /**
     * Constructeur de la pomme
     * @param etage Etage de la pomme
     * @param position Position de la pomme
     * @param nom Nom de la pomme
     */
    public Apple(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }
}
