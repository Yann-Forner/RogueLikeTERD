package Model.Entitys.Items.Foods;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;

public class Soda extends AbstractFood {
    /**
     * Constructeur de la nourriture
     *
     * @param etage    Etage de la nourriture
     * @param position Position de la nourriture
     * @param nom      Nom de la nourriture
     * @param heal     Quantité de pv restoré
     */
    public Soda(Etage etage, Position position, String nom, int heal) {
        super(etage, position, nom, heal);
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.setEndurence(Math.min(100, player.getEndurence() + getHeal()));
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
