package Model.Entitys.Items.Weapons;

import Model.Entitys.Items.AbstractItem;
import Model.Map.Etage;
import Model.Utils.Procedure;

/**
 * Factory permettant de générer les armes.
 * @author JP
 */
public class WeaponFactory {

    /**
     * Enumération des styles d'armes. (Arc, épées...)
     *
     * @author JP
     */
    public enum WeaponType {
        KNIFE("\uD83D\uDD2A"),
        SWORD(	"\u2694"),
        BOW(	"\uD83C\uDFF9"),
        SHIELD("\uD83D\uDEE1");

        private String value;

        /**
         * Constructeur de l'énumération du style d'arme.
         *
         * @author JP
         */
        WeaponType(final String value) { this.value = value; }

        /**
         * Retourne la valeur de l'énumération
         *
         * @return Retourne la valeur texte de l'énumération
         * @author JP
         */
        public String getValue() { return value; }
        @Override
        public String toString() { return this.getValue(); }
    }

    /**
     * Enumération des styles de projectiles. (Boules de feu, flèches...)
     *
     * @author JP
     */
    public enum WeaponProjectilesStyle {
        FIREBALL("\u2600");

        private String value;
        /**
         * Constructeur de l'énumération du style de projectile.
         *
         * @author JP
         */
        WeaponProjectilesStyle(final String value) { this.value = value; }
        /**
         * Retourne la valeur de l'énumération
         *
         * @return  Retourne la valeur texte de l'énumération
         * @author JP
         */
        public String getValue() { return value; }

        @Override
        public String toString() { return getValue(); }
    }

    /**
     * Retourne la liste de potions
     *
     * @param etage Etage ou aura lieu la génération de l'arme
     * @param i Type d'arme à générer
     * @return Retourne l'arme généré
     * @author JP
     */
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
