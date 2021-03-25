package Model.Entitys.Inventaires;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Weapon> armes;
    private ArrayList<Armures> armures;
    private ArrayList<Consomables> consomables;
    private ArrayList<Utilitaires> utilitaires;

    private Weapon currentWeapon=null;
    private Weapon currentArmure=null;

    public Inventory(){
        armes = new ArrayList<>();
        armures = new ArrayList<>();
        consomables = new ArrayList<>();
        utilitaires = new ArrayList<>();
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Weapon getCurrentArmure() {
        return currentArmure;
    }

    public Consomables useConsomables(int index){
        Consomables conso = consomables.get(index);
        consomables.remove(index);
        return conso;
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

    public ArrayList<Utilitaires> getObjets_utilisables() {
        return utilitaires;
    }
}
