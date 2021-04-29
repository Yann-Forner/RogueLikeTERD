package Model.Entitys.Items.Weapons;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Melee extends AbstractWeapon{

    /**
     * Constructeur de l'arme au corps a corps.
     * @param etage    Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom      Nom de l'arme
     * @param type     Type de l'arme
     * @param strength Puissance de l'arme
     * @param range    Portée de l'arme
     */
    public Melee(Etage etage, Position position, String nom, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, nom, type,strength, range);
    }

    @Override
    public void useItem(BasicPlayer player) {

    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🗡️";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"S";
        }
    }
}
