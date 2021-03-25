package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class MonsterFactory {
    public enum MonsterType {
        GHOST
    }

    private static Ghost getNewGhost(Etage etage, Position pos){
        return new Ghost(etage,pos, 2);
    }

    public static Ghost getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage));
            }
            default -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage));
            }
        }
    }
}
