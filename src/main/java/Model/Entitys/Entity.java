package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Position;

import java.io.Serializable;

/**
 * Entité de base du jeu peut etre un Mob ou un Objet.
 * @author Yann, Quentin, JP
 */
public abstract class Entity implements Serializable {
    private Position position;
    private Etage etage;
    private String nom;

    /**
     * Crée une Entité.
     * @param etage Etage
     * @param position Position
     * @param nom Nom
     * @author Yann, JP
     */
    public Entity(Etage etage, Position position, String nom) {
        this.position = position;
        this.etage = etage;
        this.nom = nom;
    }

    /**
     * Defini le comportement de l'entité lorqu'une autre lui rentre dedans.
     * @param e Entité
     * @author Quentin
     */
    public abstract void onContact(Entity e);
    public abstract String toString();

    /**
     * Renvoit sa position.
     * @return Position
     * @author Yann
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Change sa position.
     * @param position Position
     * @author Yann
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Renvoit son etage.
     * @return Etage
     * @author Yann
     */
    public Etage getEtage() {
        return etage;
    }

    /**
     * Defini son etage.
     * @author Yann
     */
    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    /**
     * Renvoit son nom.
     * @return Nom
     * @author Quentin
     */
    public String getNom() {
        return nom;
    }

    /**
     * Defini son etage.
     * @author Quentin
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}
