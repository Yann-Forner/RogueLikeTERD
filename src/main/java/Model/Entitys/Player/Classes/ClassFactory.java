package Model.Entitys.Player.Classes;

import Model.Entitys.Player.Player;

/**
 * Factory qui permet de créer des classes.
 * @author Quentin
 */
public class ClassFactory {
    public enum Class{
        WARRIOR, ARCHER, MAGICIAN
    }

    /**
     * Methode qui permet de crée le joueur avec la bonne classe.
     * @param nom Nom du joueur
     * @param classe Classe du joueur
     * @return Joueur
     * @author Quentin
     */
    public static Player getNewPlayer(String nom, Class classe){
        switch (classe){
            case WARRIOR -> {
                return new Player(nom,new Warrior());
            }
            case ARCHER -> {
                return new Player(nom,new Archer());
            }
            case MAGICIAN -> {
                return new Player(nom,new Magician());
            }
            default -> {
                return null;
            }
        }
    }
}
