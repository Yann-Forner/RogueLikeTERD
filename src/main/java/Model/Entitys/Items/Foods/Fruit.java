package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

public class Fruit extends AbstractFood{

    /**
     * Constructeur d'un fruit.
     * @param etage    Etage de la nourriture
     * @param position Position de la nourriture
     * @param foodType Quantité de pv restoré en %
     */
    public Fruit(Etage etage, Position position, FoodFactory.FoodType foodType) {
        super(etage, position, foodType);
    }
}
