package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Position;

public class Ghost extends Entity {
    public Ghost(Etage m, Position pos) {
        super(m, pos);
    }

    @Override
    public void updateEntity() {

    }

    @Override
    public String toString() {
        return null;
    }
}
