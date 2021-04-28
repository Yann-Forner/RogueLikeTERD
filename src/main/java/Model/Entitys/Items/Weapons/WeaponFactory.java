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
        SWORD(	"\u2694 "),
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
     * Retourne l'arme générée avec les caractéristiques indiquées
     *
     * @param etage Etage ou aura lieu la génération de l'arme
     * @param i Type d'arme à générer
     * @param durability Durabilitée de l'arme
     * @param range Portée de l'arme
     * @param strength Force de l'arme
     * @return Retourne l'arme généré
     * @author JP
     */
    public static AbstractItem getNewWeapon(Etage etage, WeaponType i, int strength, int durability, int range) {
        switch(i) {
            case SWORD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Epee", WeaponType.SWORD, strength, durability, range);
            }
            case KNIFE -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Couteau", WeaponType.KNIFE, strength, durability, range);
            }
            case BOW -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Arc", WeaponType.BOW, strength, durability, range);
            }
            case SHIELD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Bouclier", WeaponType.SHIELD, strength, durability, range);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Retourne l'arme générée avec des caractéristiques par défaut
     *
     * @param etage Etage ou aura lieu la génération de l'arme
     * @param i Type d'arme à générer
     * @return Retourne l'arme généré
     * @author JP
     */
    public static AbstractItem getNewWeapon(Etage etage, WeaponType i) {
        switch(i) {
            case SWORD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Epee", WeaponType.SWORD, 10, 1, 10);
            }
            case KNIFE -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Couteau", WeaponType.KNIFE, 10, 1, 10);
            }
            case BOW -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Arc", WeaponType.BOW, 10, 4, 10);
            }
            case SHIELD -> {
                return new Weapon(etage, Procedure.getAccesibleRandomPosition(true, etage), "Bouclier", WeaponType.SHIELD, 0, 0, 1000);
            }
            default -> {
                return null;
            }
        }
    }
}
