package Model.Entitys.Items.Potions;

import Model.Entitys.AbstractItem;
import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Items.Potions.InvulPotion;
import Model.Entitys.Items.Potions.StrengthPotion;
import Model.Map.Etage;
import Model.Utils.Procedure;

public class PotionFactory {
    public enum ItemType {
        HEAL_POTION, INVUL_POTION, STRENGTH_POTION
    }

    public static AbstractItem getNewPotion(Etage etage, ItemType i) {
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
            default -> {
                return null;
            }
        }
    }
}
