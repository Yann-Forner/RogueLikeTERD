package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractItem;
import Model.Entitys.Items.Weapons.Weapon;
import Model.Map.Etage;
import Model.Utils.Procedure;

public class WeaponFactory {

    public enum WeaponType {
        KNIFE("\uD83D\uDD2A"),
        SWORD(	"\u2694"),
        BOW(	"\uD83C\uDFF9"),
        SHIELD("\uD83D\uDEE1");

        private String value;
        WeaponType(final String value) { this.value = value; }
        public String getValue() { return value; }
        @Override
        public String toString() { return this.getValue(); }
    }

    public enum WeaponProjectilesStyle {
        FIREBALL("\u2600");

        private String value;
        WeaponProjectilesStyle(final String value) { this.value = value; }
        public String getValue() { return value; }

        @Override
        public String toString() { return getValue(); }
    }

    public static AbstractItem getNewWeapon(Etage etage, WeaponType i) {
        switch(i) {
            case SWORD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Epee", WeaponType.SWORD);
            }
            case KNIFE -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Couteau", WeaponType.KNIFE);
            }
            case BOW -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Arc", WeaponType.BOW);
            }
            case SHIELD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Bouclier", WeaponType.SHIELD);
            }
            default -> {
                return null;
            }
        }
    }
}
