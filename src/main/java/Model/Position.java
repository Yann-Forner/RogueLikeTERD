package Model;

public class Position {
    private final int x ;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Renvoit la Position egale a ma somme de this et de la Position pos.
     * @param pos Position
     * @return Position
     */
    public Position somme(Position pos){
        return new Position(x+pos.getX(),y+pos.getY());
    }

    /**
     * Renvoit la Position egale a ma somme de this.x + x et this.y + y.
     * @param x int
     * @param y int
     * @return Position
     */
    public Position somme(int x, int y) {
        return somme(new Position(x,y));
    }

    /**
     * Renvoit une copie de this.
     * @return Position
     */
    public Position copyOf(){
        return new Position(x,y);
    }

    public int getX() {
        return x;
    }

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
    public String toString() {
        return "Position{x:"+x+" ; y:"+y+"}";
    }

}
