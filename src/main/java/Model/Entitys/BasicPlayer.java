package Model.Entitys;

import Model.Map.Etage;
import Model.Position;

public class BasicPlayer extends Entity {

    public BasicPlayer(Etage etage, Position position) {
        super(etage,position);
        etage.get(position).setEntity(this);
    }

    public void update(Etage etage,Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    @Override
    public String toString() {
        return "\uD83E\uDD13";
    }

}
