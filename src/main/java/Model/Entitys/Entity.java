package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Start;
import org.json.JSONObject;

public abstract class Entity {
    private Position position;
    private Etage etage;
    private String nom;

    public Entity(Etage etage, Position position, String nom) {
        this.position = position;
        this.etage = etage;
        this.nom = nom;
    }

    public abstract void onContact(Entity e);
    public abstract String toString();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("position",position.toJSON());
        json.put("etage", Start.getMap().getEtages().indexOf(etage));
        json.put("nom",nom);
        return json;
    }
}
