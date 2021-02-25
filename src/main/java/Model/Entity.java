package Model;

public abstract class Entity {

    protected Position position;
    protected String drawable;

    public Entity(Position position, String drawable) {
        this.position = position;
        this.drawable = drawable;
    }

    public String drawEntity() {
        return drawable;
    }


    protected void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public Position getPosition() {
        return position;
    }

    protected void setPosition(Position position) {
        this.position = position;
    }



    /* Pour que ce soit moins chiant */
    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    protected void setPositionX(int x) {
        setPosition(new Position(x, getPosition().getY()));
    }

    protected void setPositionY(int y) {
        setPosition(new Position(getPosition().getX(), y));
    }
}