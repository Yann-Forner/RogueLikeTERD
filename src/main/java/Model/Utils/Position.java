package Model.Utils;

import Model.Map.Etage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Positions.
 * @author Yann, Quentin
 */
public class Position implements Serializable {
    private final int x;
    private final int y;

    public enum Direction{
        HAUT(new Position(0,-1)),
        BAS(new Position(0,1)),
        DROITE(new Position(1,0)),
        GAUCHE(new Position(-1,0));

        private final Position vecteur;

        Direction(Position vecteur){
            this.vecteur = vecteur;
        }

        public Position getVecteur(){
            return vecteur;
        }
    }


    /**
     * Crée un position
     * @param x Horizontal
     * @param y Vertical
     * @author Yann
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Renvoit la Position egale a ma somme de this et de la Position pos.
     * @param pos Position
     * @return Position
     * @author Quentin
     */
    public Position somme(Position pos){
        return new Position(x+pos.getX(),y+pos.getY());
    }

    /**
     * Renvoit la Position egale a ma somme de this.x + x et this.y + y.
     * @param x int
     * @param y int
     * @return Position
     * @author Quentin
     */
    public Position somme(int x, int y) {
        return somme(new Position(x,y));
    }

    /**
     * Renvoit une copie de this.
     * @return Position
     * @author Quentin
     */
    public Position copyOf(){
        return new Position(x,y);
    }

    /**
     * Calcul la distance euclidienne entre pos et this.
     * @param pos Position
     * @return Distance
     * @author Quentin
     */
    public double Distance(Position pos){
        return Math.sqrt(Math.pow((pos.getX() - getX()), 2) + Math.pow((pos.getY() - getY()), 2));
    }

    /**
     * Renvoit la liste des voisins de this.
     * @param e Etage
     * @return ArrayList<Position>
     * @author Quentin
     */
    public ArrayList<Position> voisins(Etage e){
        ArrayList<Position> voisins = new ArrayList<>();
        voisins.add(somme(0, -1));
        voisins.add(somme(0, 1));
        voisins.add(somme(1, 0));
        voisins.add(somme(-1, 0));
        voisins.add(somme(-1, 1));
        voisins.add(somme(1, 1));
        voisins.add(somme(1, -1));
        voisins.add(somme(-1, -1));
        return voisins.stream().filter(p -> (p.getX() >= 0 && p.getY() >= 0 && p.getX() < e.getWidth() && p.getY() < e.getHeigth())).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Renvoit x.
     * @return int
     * @author Yann
     */
    public int getX() {
        return x;
    }

    /**
     * Renvoit y.
     * @return int
     * @author Yann
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position){
            Position pos= (Position) obj;
            return (x==pos.getX() && y==pos.getY());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public String toString() {
        return "Position{x:"+x+" ; y:"+y+"}";
    }

}
