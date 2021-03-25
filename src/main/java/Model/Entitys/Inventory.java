package Model.Entitys;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Weapon> armes;
    private ArrayList<Armures> armures;
    private ArrayList<Consomables> consomables;
    private ArrayList<Utilitaires> utilitaires;

    public Inventory(){
        armes = new ArrayList<>();
        armures = new ArrayList<>();
        consomables = new ArrayList<>();
        utilitaires = new ArrayList<>();
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
