package Model.Entitys.Items;

import Model.Entitys.AbstractItem;
import Model.Map.Etage;
import Model.Utils.Procedure;

public class ItemFactory {
    public enum ItemType {
        HEAL_POTION, INVUL_POTION, STRENGTH_POTION, STACK_OF_MONEY
    }

    public static AbstractItem getNewItem(Etage etage, ItemType i) {
        switch(i) {
            case HEAL_POTION -> {
                return new HealPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de heal", false, 25);
            }
            case INVUL_POTION -> {
                return new InvulPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion d'invulnérabilité", false);
            }
            case STRENGTH_POTION -> {
                return new StrengthPotion(etage, Procedure.getAccesibleRandomPosition(true, etage), "Potion de force", false, 50);
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
