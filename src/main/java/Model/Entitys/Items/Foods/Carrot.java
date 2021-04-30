package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Consommable de nourriture
 * @author JP
 */
public class Carrot extends AbstractFood {
    /**
     * Constructeur de la carotte
     * @param etage Etage de la carotte
     * @param position Position de la carotte
     * @param nom Nom de la carotte
     */
    public Carrot(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom, heal);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🥕";
        }
        else{
            return super.toString()+"p";
        }
    }
}
