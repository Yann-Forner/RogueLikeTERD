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

    public L getLeft(){
        return left;
    }

    public R getRight(){
        return right;
    }

    public void setLeft(L left) {
        this.left = left;
    }

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
