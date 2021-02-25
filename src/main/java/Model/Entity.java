package Model;

public abstract class Entity {

    protected Position Position;
    protected Map CurrentMap;

    public Entity(Position position, Map currentMap) {
        this.Position = position;
        this.CurrentMap = currentMap;
    }

    public Position getPosition() {
        return Position;
    }

    protected void setPosition(Position position) {
        this.Position = position;
    }



    /* Pour que ce soit moins chiant */
    public int getPositionX() {
        return Position.getX();
    }

    public int getPositionY() {
        return Position.getY();
    }

    protected void setPositionX(int x) {
        setPosition(new Position(x, getPosition().getY()));
    }

    protected void setPositionY(int y) {
        setPosition(new Position(getPosition().getX(), y));
    }
}