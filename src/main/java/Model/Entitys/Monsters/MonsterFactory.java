package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

public class MonsterFactory {
    public enum MonsterType {
        GHOST, ZOMBIE, SPIDER
    }

    private static Ghost getNewGhost(Etage etage, Position pos, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Ghost(etage,pos,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Zombie getNewZombie(Etage etage, Position pos, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Zombie(etage,pos,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Rat getNewSpider(Etage etage, Position pos, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Rat(etage,pos,vision_radius,Agro,update_rate_ms,path_type);
    }

    public static AbstractMonster getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),10,10,700, Tools.PATH_CROSS);
            }
            case ZOMBIE -> {
                return getNewZombie(etage, Procedure.getAccesibleRandomPosition(true,etage),5,30,1500, Tools.PATH_CROSS);
            }
            case SPIDER -> {
                return getNewSpider(etage, Procedure.getAccesibleRandomPosition(true,etage),15,15,600, Tools.PATH_DIAG);
            }
            default -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),5,5,700,Tools.PATH_CROSS);
            }
        }
    }
}
