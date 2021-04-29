package Model.Entitys.Items.Weapons;

import Model.Entitys.Items.AbstractItem;
import Model.Map.Etage;
import Model.Utils.Procedure;

/**
 * Factory permettant de générer les armes.
 * @author JP, Quentin
 */
public class WeaponFactory {
    /**
     * Enumération des styles d'armes. (Arc, épées...).
     * @author JP
     */
    public enum WeaponType {
        KNIFE,
        SWORD,
        BOW,
        SHIELD,
        WAND
    }

    /**
     * Retourne l'arme générée avec des caractéristiques par défaut.
     * @param etage Etage ou aura lieu la génération de l'arme
     * @param type Type d'arme à générer
     * @return Retourne l'arme généré
     * @author JP
     */
    public static AbstractItem getNewWeapon(Etage etage, WeaponType type) {
        switch(type) {
            case SWORD -> {
                return new Melee(etage, Procedure.getAccesibleRandomPosition(true, etage), "Epee", type, 10, 1);
            }
            case KNIFE -> {
                return new Melee(etage, Procedure.getAccesibleRandomPosition(true, etage), "Couteau", type, 10, 1);
            }
            case BOW -> {
                return new Bow(etage, Procedure.getAccesibleRandomPosition(true, etage), "Arc", type,10, 4);
            }
            case SHIELD -> {
                return new Melee(etage, Procedure.getAccesibleRandomPosition(true, etage), "Bouclier", type, 0, 0);
            }
            case WAND -> {
                return new Wand(etage, Procedure.getAccesibleRandomPosition(true, etage), "Baguette", type, 5, 10);
            }
            default -> {
                return null;
            }
        }
    }
}
