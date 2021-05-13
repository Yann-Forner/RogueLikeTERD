package Model.Entitys.Items.Weapons;

import Model.Entitys.Items.AbstractItem;
import Model.Map.Etage;
import Model.Utils.Procedure;
import Model.Utils.Start;

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
        SWORD,
        BOW,
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
        return switch(type) {
            case SWORD -> new Melee(etage, Procedure.getAccesibleRandomPosition(true, etage), "Epée", type, 10*getBaseLvl(), Procedure.getRandomInt(5,1));
            case BOW -> new Bow(etage, Procedure.getAccesibleRandomPosition(true, etage), "Arc", type,10*getBaseLvl(), Procedure.getRandomInt(15,2));
            case WAND -> new Wand(etage, Procedure.getAccesibleRandomPosition(true, etage), "Baguette", type, 5*getBaseLvl(), Procedure.getRandomInt(10,1));
        };
    }

    /**
     * Retourne le level de base
     * @return int
     * @author Quentin
     */
    private static int getBaseLvl(){
        return Start.getMap() == null ? 1 : Procedure.getRandomInt(3,0)+Start.getMap().getIndexCurrent()+1;
    }
}
