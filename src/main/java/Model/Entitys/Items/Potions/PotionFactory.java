package Model.Entitys.Items.Potions;

import Model.Entitys.AbstractItem;
import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Items.Potions.InvulPotion;
import Model.Entitys.Items.Potions.StrengthPotion;
import Model.Map.Etage;
import Model.Utils.Procedure;

public class PotionFactory {
    public enum ItemType {
        HEAL_POTION, INVUL_POTION, STRENGTH_POTION, STACK_OF_MONEY
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
            case STACK_OF_MONEY -> {
                return new StackOfMoney(etage,Procedure.getAccesibleRandomPosition(true,etage),"Tas d'argent",(10 + (int)(Math.random() * ((50 - 10) + 1))));
            }
            default -> {
                return null;
            }
        }
    }
}
