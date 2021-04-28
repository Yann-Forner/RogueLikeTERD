package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Consommable de nourriture
 * @author JP
 */
public class Banana extends AbstractFood {
    /**
     * Constructeur de la banane
     * @param etage Etage de la banane
     * @param position Position de la banane
     * @param nom Nom de la banane
     */
    public Banana(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom, heal);
    }

    @Override
    public String toString() {
        return 	"\uD83C\uDF4C";
    }
}
