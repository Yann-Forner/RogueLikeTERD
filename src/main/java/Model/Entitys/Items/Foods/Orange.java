package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Consommable de nourriture
 * @author JP
 */
public class Orange extends AbstractFood {
    /**
     * Constructeur de l'orange
     * @param etage Etage de l'orange
     * @param position Position de l'oange
     * @param nom Nom de l'oange
     */
    public Orange(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }
}
