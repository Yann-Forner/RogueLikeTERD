package Model.Entitys.Items.Foods;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;

public class Burger extends AbstractFood {
    /**
     * Constructeur de la nourriture
     * @param etage    Etage de la nourriture
     * @param position Position de la nourriture
     * @param foodType Type de la nourriture
     */
    public Burger(Etage etage, Position position, FoodFactory.FoodType foodType) {
        super(etage, position, foodType);
    }

    @Override
    public void useItem(Player player) {
        int enduMax = player.getMAX_ENDURENCE();
        int enduConverted = enduMax / 100 * player.getEndurence();
        player.updateEndurence(enduConverted);
    }
}
