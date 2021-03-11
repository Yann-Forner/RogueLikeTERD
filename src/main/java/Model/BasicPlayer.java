package Model;

import Model.Map.Etage;

public class BasicPlayer extends Entity {

    public BasicPlayer(Etage etage, Position position) {
        super(etage,position);
    }

    @Override
    public String toString() {
        return Affichage.BOLD+ "\u001b[38;5;22m"+'@'+Affichage.RESET;
    }
}
