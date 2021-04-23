package Model.Entitys.Player.Classes;

import Model.Entitys.Player.BasicPlayer;

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
    public static BasicPlayer getNewPlayer(String nom, Class classe){
        switch (classe){
            case WARRIOR -> {
                return new BasicPlayer(nom,new Warrior());
            }
            case ARCHER -> {
                return new BasicPlayer(nom,new Archer());
            }
            case MAGICIAN -> {
                return new BasicPlayer(nom,new Magician());
            }
            default -> {
                return null;
            }
        }
    }
}
