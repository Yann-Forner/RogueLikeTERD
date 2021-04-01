package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class MonsterFactory {
    public enum MonsterType {
        GHOST,
    }

    private static Ghost getNewGhost(Etage etage, Position pos, int vision_radius, int Agro){
        return new Ghost(etage,pos,vision_radius,Agro);
    }

    public static Ghost getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),10,10);
            }
            default -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),5,5);
            }
        }
    }
}
