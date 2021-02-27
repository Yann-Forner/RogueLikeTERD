package Model;

public class BasicPlayer extends Entity {

    public BasicPlayer(Position position) {
        super(position);
    }

    /*TODO deplacer tout ça
    private boolean checkIfNewPositionAccessible(Position targetPosition) {
        return getCurrentMap().Cells.get(targetPosition.getY()).get(targetPosition.getX()).isAccesible();
    }

    public void move(Position newPosition) {
        if(checkIfNewPositionAccessible(newPosition))
            setPosition(newPosition);
    }*/

    @Override
    public String toString() {
        return "\u001B[32m@";
    }
}
