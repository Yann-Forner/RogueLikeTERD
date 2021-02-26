package Model;

public class Position {
    private int x ;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position somme(Position p){
        return new Position(x+p.getX(),y+p.getY());
    }

    public Position somme(int x, int y) {
        return somme(new Position(x,y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{x:"+x+" ; y:"+y+"}";
    }

}
