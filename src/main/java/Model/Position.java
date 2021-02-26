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

    public static Position copyOf(Position pos){
        return new Position(pos.getX(), pos.getY());
    }

    public static int Distance(Position p1, Position p2){
        return (int)Math.sqrt(Math.pow((p2.getX()- p1.getX()),2)+Math.pow((p2.getY()- p1.getY()),2));
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
