package Model.Entitys.Inventaires;

import Model.Entitys.Items.Foods.AbstractFood;
import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Inventaire du joueur. C'est là que sont contenu les listes de potions, d'armes, de nourritures...
 * @author JP
 */
public class Inventory implements Serializable {
    private ArrayList<Weapon> weapons;
    private ArrayList<AbstractFood> foods;
    private ArrayList<AbstractPotion> potions;


    public Inventory(){
        weapons = new ArrayList<>();
        foods = new ArrayList<>();
        potions = new ArrayList<>();
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
     * Fais rouler la liste de nourritures afin de se déplacer vers le deuxième élement.
     *
     * @author JP
     */    public void switchFoods() {
        var first = foods.remove(0);
        foods.add(first);
    }

    /**
     * Ajoute une potion a la liste de potions
     *
     * @param i Potion à ajouter a la liste de potions
     * @author JP
     */
    public void addPotion(AbstractPotion i){
        potions.add(i);
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     *
     * @param w Arme à ajouter a la liste d'armes
     * @author JP
     */
    public void addWeapon(Weapon w){
        weapons.add(w);
    }

    /**
     * Retourne la liste de potions
     *
     * @author JP
     */
    public ArrayList<AbstractPotion> getPotions() {
        return potions;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<AbstractFood> getFoods() {
        return foods;
    }
}
