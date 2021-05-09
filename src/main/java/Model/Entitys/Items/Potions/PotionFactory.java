package Model.Entitys.Items.Potions;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Foods.AbstractFood;
import Model.Map.Etage;
import Model.Utils.Procedure;

/**
 * Factory permettant de générer les potions, ramassables par le joueur
 * @author JP
 */

public class PotionFactory {
    public enum PotionType {
        HEAL_POTION, INVUL_POTION, STRENGTH_POTION,
    }

    /**
     * Générateur de potion
     *
     * @param etage Etage où se situe la potion
     * @param i Type de la potion
     * @return Retourne la potion générée
     * @author JP
     */
    public static AbstractPotion getNewPotion(Etage etage, PotionType i) {
        switch(i) {
            case HEAL_POTION -> {
                return new HealPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de heal", 25);
            }
            case INVUL_POTION -> {
                return new InvulPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion d'invulnérabilité");
            }
            case STRENGTH_POTION -> {
                return new StrengthPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de force", 50);
            }
            default -> throw new IllegalStateException("Unexpected PotionType: " + i);
        }
    }
}
