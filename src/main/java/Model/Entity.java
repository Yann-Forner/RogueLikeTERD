package Model;

public abstract class Entity {

    protected Position Position;

    public Entity(Position position) {
        this.Position = position;
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

    public void move(Position pos) {
        move(new Position(pos.getX(), pos.getY()));
    }

    public void moveLeft() {
        move(new Position(getPositionX() - 1, getPositionY()));
    }

    public void moveRight() {
        move(new Position(getPositionX() + 1, getPositionY()));
    }

    public void moveUp() {
        move(new Position(getPositionX(), getPositionY() - 1));
    }

    public void moveDown() {
        move(new Position(getPositionX(), getPositionY() + 1));
    }
}