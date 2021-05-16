package Model.Utils;

/**
 * Paire d'objets.
 * @param <L> Objet de gauche.
 * @param <R> Objet de droite.
 * @author Quentin
 */
public class Pair<L,R>{
    private L left;
    private R right;

    public Pair(L left, R rigth){
        this.left = left;
        this.right = rigth;
    }

    /**
     * Renvoit la partie gauche.
     * @return partie gauche
     * @author Quentin
     */
    public L getLeft(){
        return left;
    }

    /**
     * Renvoit la partie droite.
     * @return partie droite
     * @author Quentin
     */
    public R getRight(){
        return right;
    }

    /**
     * Defini la partie gauche.
     * @param left Partie gauche.
     * @author Quentin
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * Defini la partie droite.
     * @param right Partie droite.
     * @author Quentin
     */
    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
