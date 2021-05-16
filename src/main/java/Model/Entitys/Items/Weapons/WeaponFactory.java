package Model.Entitys.Items.Weapons;

import Model.Map.Etage;
import Model.Utils.Procedure;
import Model.Utils.Start;

import java.util.Objects;

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
    public static AbstractWeapon getNewWeapon(Etage etage, WeaponType type) {
        return switch(type) {
            case SWORD -> new Melee(etage, Procedure.getAccesibleRandomPosition(true, etage), type, 10*getBaseLvl(), Procedure.getRandomInt(5,1), getRandomPrix());
            case BOW -> new Bow(etage, Procedure.getAccesibleRandomPosition(true, etage), type,10*getBaseLvl(), Procedure.getRandomInt(15,2), getRandomPrix());
            case WAND -> new Wand(etage, Procedure.getAccesibleRandomPosition(true, etage), type, 5*getBaseLvl(), Procedure.getRandomInt(10,1), getRandomPrix());
        };
    }

    /**
     * Retourne le level de base.
     * @return int
     * @author Quentin
     */
    private static int getBaseLvl(){
        return Start.getMap() == null ? 1 : Procedure.getRandomInt(3,0) + Start.getMap().getIndexCurrent()+1;
    }

    /**
     * Retourne le prix de base.
     * @return int
     * @author Quentin
     */
    private static int getRandomPrix(){
        return Procedure.getRandomInt(30,0) * (Start.getMap()==null ? 1 : Objects.requireNonNull(Start.getMap()).getIndexCurrent()+1);
    }
}
