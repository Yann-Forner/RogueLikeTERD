package Model.Entitys.Inventaires;

import Model.Entitys.AbstractItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private ArrayList<Weapon> armes;
    private ArrayList<Armures> armures;
    private ArrayList<Consomables> consomables;
    private ArrayList<AbstractItem> items;

    private Weapon currentWeapon=null;
    private Armures currentArmure=null;

    public Inventory(){
        armes = new ArrayList<>();
        armures = new ArrayList<>();
        consomables = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void addItem(AbstractItem i){
        items.add(i);
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Armures getCurrentArmure() {
        return currentArmure;
    }

    public Consomables useConsomables(int index){
        Consomables conso = consomables.get(index);
        consomables.remove(index);
        return conso;
    }

    public int getWeaponDamages(){
        return currentWeapon.getDegats();
    }

    public int getArmureProtection(){
        return currentArmure.getProtection();
    }

    public ArrayList<Weapon> getArmes() {
        return armes;
    }

    public ArrayList<Armures> getArmures() {
        return armures;
    }

    public ArrayList<Consomables> getObjets_temporaires() {
        return consomables;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        if(this.currentWeapon!=null){
            addArme(this.currentWeapon);
            this.getArmes().remove(currentWeapon);
        }
        this.currentWeapon = currentWeapon;
    }

    public void setCurrentArmure(Armures currentArmure) {
        if (this.currentArmure!=null){
            addArmure(this.currentArmure);
            this.getArmures().remove(currentArmure);
        }
        this.currentArmure = currentArmure;
    }

    public Inventory addArme(Weapon weapon){
        if (this.getArmes().size()==0)this.setCurrentWeapon(weapon);
        else this.getArmes().add(weapon);
        return this;
    }

    public Inventory addArmure(Armures armures){
        if(this.getArmures().size()==0)this.setCurrentArmure(armures);
        else this.getArmures().add(armures);
        return this;
    }
    public Inventory addConsomables(Consomables c){
        this.getObjets_temporaires().add(c);
        return this;
    }

}
