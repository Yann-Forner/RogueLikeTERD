package Model.Entitys.Items.Foods;

import Model.Entitys.Entity;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Sound;

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
        onContact(player);
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof BasicPlayer) {
            super.onContact(e);
            BasicPlayer player = ((BasicPlayer) e);
            int enduMax = ((BasicPlayer) e).getMAX_ENDURENCE();
            int healConverted = enduMax / 100 * getHeal();

            player.updateEndurence(healConverted);
            getEtage().removeItem(this);
            Sound.playAudio(Sound.Sons.MANGER,0);
        }
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
