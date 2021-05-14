package Model.Entitys.Player.Classes;

import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;

/**
 * L'archer est le plus rapide et peut attaquer de loin mais en contrepartie il a tres peu de points de vie.
 * @author Quentin
 */
public class Archer extends AbstractClass{

    @Override
    public int getVisionRadius() {
        return 100;
    }

    @Override
    public int getBasePV() {
        return 50;
    }

    @Override
    public int getBaseForce() {
        return 6;
    }

    @Override
    public int getSpeed() {
        return 50;
    }

    @Override
    public void setBaseItems(Player player) {
        player.getInventory().addWeapon(WeaponFactory.getNewWeapon(player.getEtage(), WeaponFactory.WeaponType.BOW));
    }

    @Override
    public boolean canUse(AbstractWeapon weapon) {
        return weapon.getType() == WeaponFactory.WeaponType.BOW;
    }

    @Override
    public int getEndurenceRate() {
        return 900;
    }

    @Override
    public String getNom() {
        return "L'Archer";
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🧝";
        }
        else{
            return super.toString() + "@";
        }
    }
}
