package Model.Entitys.Player.Classes;

import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;

/**
 * Le guerrier est celui qui as le plus de points de vie mais le moins d'attaque.
 * @author Quentin
 */
public class Warrior extends AbstractClass {

    @Override
    public int getVisionRadius() {
        return 100;
    }

    @Override
    public int getBasePV() {
        return 100;
    }

    @Override
    public int getBaseForce() {
        return 3;
    }

    @Override
    public int getSpeed() {
        return 100;
    }

    @Override
    public void setBaseItems(Player player) {
        player.getInventory().addWeapon(WeaponFactory.getNewWeapon(player.getEtage(), WeaponFactory.WeaponType.SWORD));
    }

    @Override
    public boolean canUse(AbstractWeapon weapon) {
        return weapon.getType() == WeaponFactory.WeaponType.SWORD;
    }

    @Override
    public int getEndurenceRate() {
        return 700;
    }

    @Override
    public String getNom() {
        return "Le Guerrier";
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "👹";
        }
        else{
            return super.toString() + "@";
        }
    }
}
