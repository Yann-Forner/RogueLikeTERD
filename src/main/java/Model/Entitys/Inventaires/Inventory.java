package Model.Entitys.Inventaires;

import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.BasicPlayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Inventaire du joueur. C'est là que sont contenu les listes de potions, d'armes, de nourritures...
 * @author JP
 */
public class Inventory implements Serializable {
    private final ArrayList<AbstractWeapon> weapons;
    private final ArrayList<AbstractPotion> potions;


    /**
     * Constructeur d'inventory. Initialise les listes.
     *
     * @author JP
     */
    public Inventory(){
        weapons = new ArrayList<>();
        potions = new ArrayList<>();
    }


    /**
     * Utilise l'arme courante
     */
    public void useCurrentWeapon(BasicPlayer player) {
        if(weapons.size() > 0)
            weapons.get(0).useItem(player);
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     *
     * @author JP
     */
    public void switchWeapons() {
        var first = weapons.remove(0);
        weapons.add(first);
    }

    /**
     * Fais rouler la liste de pootions afin de se déplacer vers le deuxième élement.
     *
     * @author JP
     */
    public void switchPotions() {
        var first = potions.remove(0);
        potions.add(first);
    }

    /**
     * Ajoute une potion a la liste de potions.
     * @param potion Potion à ajouter a la liste de potions
     * @author JP
     */
    public void addPotion(AbstractPotion potion){
        potions.add(potion);
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     *
     * @param arme Arme à ajouter a la liste d'armes
     * @author JP
     */
    public void addWeapon(AbstractWeapon arme){
        weapons.add(arme);
    }

    /**
     * Retourne la liste de potions
     *
     * @return Retourne la liste de potions
     * @author JP
     */
    public ArrayList<AbstractPotion> getPotions() {
        return potions;
    }

    /**
     * Retourne la liste d'armes
     *
     * @return Retourne la liste d'armes
     * @author JP
     */
    public ArrayList<AbstractWeapon> getWeapons() {
        return weapons;
    }

}
