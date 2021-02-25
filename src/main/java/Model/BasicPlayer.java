package Model;

public class BasicPlayer extends Entity {

    private Map currentMap;

    public BasicPlayer(Position position, String drawable, Map map) {
        super(position, drawable);
        this.currentMap = map;

        this.setDrawable("\u001B[32m@");
    }

    private boolean checkIfNewPositionAccessible(Position targetPosition) {
        return getCurrentMap().Cells.get(targetPosition.getY()).get(targetPosition.getX()).isAccesible();
    }

    public void move(Position newPosition) {
        if(checkIfNewPositionAccessible(newPosition))
            setPositionY(getPositionY() + 1);
    }



    public void move(int targetX, int targetY) {
        move(new Position(targetX, targetY));
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

    public Map getCurrentMap() {
        return currentMap;
    }
}
