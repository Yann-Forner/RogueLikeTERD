package Model.Entitys.Inventaires;

import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {
    private ArrayList<Weapon> weapons;
    private ArrayList<Armures> armors;
    private ArrayList<Consomables> foods;
    private ArrayList<AbstractPotion> potions;


    public Inventory(){
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        foods = new ArrayList<>();
        potions = new ArrayList<>();
    }

    public void switchWeapons() {
        var first = weapons.remove(0);
        weapons.add(first);
    }

    public void switchPotions() {
        var first = potions.remove(0);
        potions.add(first);
    }

    public void addPotion(AbstractPotion i){
        potions.add(i);
    }

    public void addWeapon(Weapon w){
        weapons.add(w);
    }

    public ArrayList<AbstractPotion> getPotions() {
        return potions;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Armures> getArmures() {
        return armors;
    }

    public ArrayList<Consomables> getFoods() {
        return foods;
    }
}
