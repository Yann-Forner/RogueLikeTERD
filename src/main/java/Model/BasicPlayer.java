package Model;

public class BasicPlayer extends Entity {

    public BasicPlayer(Map map,Position position) {
        super(map,position);
    }

    @Override
    public String toString() {
        return Affichage.BOLD+ "\u001b[38;5;22m"+'@'+Affichage.RESET;
    }
}
