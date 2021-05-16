package Model.Entitys.Items.Potions;

import Model.Map.Etage;
import Model.Utils.Procedure;
import Model.Utils.Start;

import java.util.Objects;

/**
 * Factory permettant de générer les potions, ramassables par le joueur
 * @author JP
 */

public class PotionFactory {
    public enum PotionType {
        HEAL_POTION, INVUL_POTION, STRENGTH_POTION, ENDURENCE_POTION,
    }

    /**
     * Générateur de potion
     * @param etage Etage où se situe la potion
     * @param i Type de la potion
     * @return Retourne la potion générée
     * @author JP
     */
    public static AbstractPotion getNewPotion(Etage etage, PotionType i) {
        return switch(i) {
            case HEAL_POTION -> new HealPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de heal", getRandomPrix(), 25);
            case INVUL_POTION -> new InvulPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion d'invulnérabilité", getRandomPrix());
            case STRENGTH_POTION -> new StrengthPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de force", getRandomPrix(), 50.0);
            case ENDURENCE_POTION -> new StaminaPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion d'endurance", getRandomPrix());
        };
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
