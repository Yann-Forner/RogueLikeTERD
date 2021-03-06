package Model;

public class Position {
    private final int x ;
    private final int y;

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

    public int Distance(Position pos){
        return (int)Math.sqrt(Math.pow((pos.getX()- x),2)+Math.pow((pos.getY()- y),2));
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
