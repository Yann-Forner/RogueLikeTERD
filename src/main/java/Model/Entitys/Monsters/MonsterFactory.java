package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

public class MonsterFactory {
    public enum MonsterType {
        GHOST, ZOMBIE, RAT, BEE, ALIEN, SNAIL , BIGMONSTER
    }

    private static Ghost getNewGhost(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Ghost(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Zombie getNewZombie(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Zombie(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Rat getNewRat(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Rat(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Bee getNewBee(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Bee(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Alien getNewAlien(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Alien(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Snail getNewSnail(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Snail(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }
    private static BigMonster getNewBigMonster(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new BigMonster(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    public static AbstractMonster getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),"GHOST", 10,3,10,10,700, Tools.PATH_GHOST);
            }
            case ZOMBIE -> {
                return getNewZombie(etage, Procedure.getAccesibleRandomPosition(true,etage),"ZOMBIE",25,5,5,30,1500, Tools.PATH_CROSS);
            }
            case RAT -> {
                return getNewRat(etage, Procedure.getAccesibleRandomPosition(true,etage),"RAT",5,1,15,15,300, Tools.PATH_DIAG);
            }
            case BEE -> {
                return getNewBee(etage, Procedure.getAccesibleRandomPosition(true,etage),"BEE",20,5,15,20,900, Tools.PATH_CROSS);
            }
            case ALIEN -> {
                return getNewAlien(etage, Procedure.getAccesibleRandomPosition(true,etage),"ALIEN",15,10,20,60,300, Tools.PATH_CROSS);
            }
            case SNAIL -> {
                return getNewSnail(etage, Procedure.getAccesibleRandomPosition(true,etage),"SNAIL",10,15,60,60,600, Tools.PATH_CROSS);
            }
            case BIGMONSTER -> {
                return getNewBigMonster(etage,Procedure.getAccesibleRandomPosition(true,etage),"BigMonster",10,18,15,20,900,Tools.PATH_CROSS);
            }
            default -> {
                return null;
            }
        }
    }

    protected static AbstractMonster getNewMonster(Etage etage, MonsterType m, int pv){
        switch (m){
            case BEE -> {
                return getNewBee(etage,Procedure.getAccesibleRandomPosition(true,etage),"BEE", pv,5,15,20,900, Tools.PATH_CROSS);
            }
            default -> {
                return null;
            }
        }
    }
}
