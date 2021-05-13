package Model.Entitys.Items.Foods;

import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.Position;

public class Soda extends AbstractFood {
    /**
     * Constructeur de la nourriture
     * @param etage    Etage de la nourriture
     * @param position Position de la nourriture
     * @param nom      Nom de la nourriture
     * @param heal     Quantité de pv restoré
     */
    public Soda(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom, heal);
    }

    @Override
    public void useItem(Player player) {
        int enduMax = player.getMAX_ENDURENCE();
        int enduConverted = enduMax / 100 * player.getEndurence();
        player.updateEndurence(enduConverted);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"\uD83E\uDD64";
        }
        else{
            return super.toString()+"s";
        }
    }
}
