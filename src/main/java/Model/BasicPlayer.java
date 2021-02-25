package Model;

public class BasicPlayer extends Entity {

    private Map CurrentMap;

    public BasicPlayer(Position position, Map map) {
        super(position, map);
        this.CurrentMap = map;
    }

    private boolean checkIfNewPositionAccessible(Position targetPosition) {
        return getCurrentMap().Cells.get(targetPosition.getY()).get(targetPosition.getX()).isAccesible();
    }

    public void move(Position newPosition) {
        if(checkIfNewPositionAccessible(newPosition))
            setPosition(newPosition);
    }

    @Override
    public String toString() {
        return "\u001B[32m@";
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
        return CurrentMap;
    }
}
