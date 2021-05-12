package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Procedure;

/**
 * Factory permettant de générer les consommables de nourritures.
 * @author JP
 */
public class FoodFactory {

    public enum FoodType {
        APPLE, BANANA, CARROT, ORANGE, SODA;
    }

    /**
     * Méthode factory pour les nourritures
     * @param etage Etage ou sera généré la nourriture
     * @param ft Type de la nourriture
     * @return Retourne la nourriture générée
     */
    public static AbstractFood getNewFood(Etage etage, FoodType ft) {
        switch(ft) {
            case APPLE -> { return new Apple(etage, Procedure.getAccesibleRandomPosition(true, etage), "Pomme", 20); }
            case BANANA -> { return new Banana(etage, Procedure.getAccesibleRandomPosition(true, etage), "Banane", 20); }
            case CARROT -> { return new Carrot(etage, Procedure.getAccesibleRandomPosition(true, etage), "Carotte", 20); }
            case ORANGE -> { return new Orange(etage, Procedure.getAccesibleRandomPosition(true, etage), "Orange", 20); }
            case SODA -> { return new Soda(etage, Procedure.getAccesibleRandomPosition(true, etage), "Soda", 10); }
            default -> throw new IllegalStateException("Unexpected FoodType: " + ft);
        }
    }
}
