package Model.Entitys.Player.Classes;

import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;

/**
 * Le magicien est le plus lent et peut faire des degats de zone.
 * @author Quentin
 */
public class Magician extends AbstractClass{

    @Override
    public int getVisionRadius() {
        return 100;
    }

    @Override
    public int getBasePV() {
        return 70;
    }

    @Override
    public int getBaseForce() {
        return 5;
    }

    @Override
    public int getSpeed() {
        return 200;
    }

    @Override
    public void setBaseItems(Player player) {
        player.getInventory().addWeapon(WeaponFactory.getNewWeapon(player.getEtage(), WeaponFactory.WeaponType.WAND));
    }

    @Override
    public boolean canUse(AbstractWeapon weapon) {
        return weapon.getType() == WeaponFactory.WeaponType.WAND;
    }

    @Override
    public int getEndurenceRate() {
        return 400;
    }

    @Override
    public String getNom() {
        return "Le Magicien";
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🧙";
        }
        else{
            return super.toString() + "@";
        }
    }
}
