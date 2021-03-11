package Model;

import Model.Map.Etage;

public class BasicPlayer extends Entity {

    public BasicPlayer(Etage etage, Position position) {
        super(etage,position);
    }

    public void updateEtage(Etage etage){
        super.etage=etage;
    }

    @Override
    public String toString() {
        return Affichage.BOLD + "\u001b[38;5;22m" + '@' + Affichage.RESET;
    }

}
