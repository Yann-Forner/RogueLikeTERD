package Model;

public class BasicPlayer extends Entity {

    public BasicPlayer(Map map,Position position) {
        super(map,position);
    }

    @Override
    public String toString() {
        return Affichage.GREEN+'@';
    }
}