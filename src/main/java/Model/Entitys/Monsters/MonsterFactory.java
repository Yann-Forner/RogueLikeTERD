package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Procedure;
import Model.Utils.Start;
import Model.Utils.Tools;

/**
 * Factory qui crée les monstres
 * @auhtor Yann,Quentin,JP
 */
public class MonsterFactory {
    public enum MonsterType {
        GHOST, ZOMBIE, RAT, BEE, ALIEN, SNAIL , BIGMONSTER ,BIRD, VOLCANO, SKULL, SNAKE, INVOQUEUR, MARCHAND
    }

    /**
     * Renvoi un nouveau monstre
     * @param etage etage courant
     * @param m enum d'un monstre
     * @return un monstre
     * @auhtor Yann,Quentin,JP
     */
    public static AbstractMonster getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return new Ghost(etage, Procedure.getAccesibleRandomPosition(true,etage),"GHOST", 10,3,10,10,700, Tools.PATH_GHOST,getBaseLvl());
            }
            case ZOMBIE -> {
                return new Zombie(etage, Procedure.getAccesibleRandomPosition(true,etage),"ZOMBIE",25,5,5,30,1500, Tools.PATH_CROSS,getBaseLvl());
            }
            case RAT -> {
                return new Rat(etage, Procedure.getAccesibleRandomPosition(true,etage),"RAT",5,1,15,15,300, Tools.PATH_DIAG, getBaseLvl());
            }
            case BEE -> {
                return new Bee(etage, Procedure.getAccesibleRandomPosition(true,etage),"BEE",20,5,15,20,900, Tools.PATH_CROSS,getBaseLvl());
            }
            case ALIEN -> {
                return new Alien(etage, Procedure.getAccesibleRandomPosition(true,etage),"ALIEN",15,2,20,60,300, Tools.PATH_CROSS,getBaseLvl());
            }
            case SNAIL -> {
                return new Snail(etage, Procedure.getAccesibleRandomPosition(true,etage),"SNAIL",10,5,60,60,600, Tools.PATH_CROSS,getBaseLvl());
            }
            case BIRD -> {
                return new Bird(etage, Procedure.getAccesibleRandomPosition(true,etage),"BIRD",10,5,60,60,600, Tools.PATH_DIAG,getBaseLvl());
            }
            case VOLCANO -> {
                return new Volcano(etage, Procedure.getAccesibleRandomPosition(true,etage),"VOLCANO",1,10,2,0,1000, Tools.PATH_CROSS,getBaseLvl());
            }
            case SKULL -> {
                return new Skull(etage, Procedure.getAccesibleRandomPosition(true,etage),"SKULL",10,3,15,20,750, Tools.PATH_CROSS,getBaseLvl());
            }
            case BIGMONSTER -> {
                return new BigMonster(etage,Procedure.getAccesibleRandomPosition(true,etage),"BigMonster",10,18,15,20,900,Tools.PATH_CROSS,getBaseLvl()+5);
            }
            case SNAKE -> {
                return new Snake(etage,Procedure.getAccesibleRandomPosition(true,etage),"Snake",10,1,15,20,900,Tools.PATH_NOMOBS,getBaseLvl()+5,10);
            }
            case INVOQUEUR -> {
                return new Invoqueur(etage,Procedure.getAccesibleRandomPosition(true,etage),"INVOQUEUR",10,18,15,20,1000,Tools.PATH_CROSS,getBaseLvl()+5);
            }
            case MARCHAND -> {
                return new Marchand(etage,Procedure.getAccesibleRandomPosition(true,etage),"MARCHAND",50,20,15,20,1000,Tools.PATH_CROSS,getBaseLvl()+5, Marchand.STATE.NOTVISITED);
            }

            default -> {
                return null;
            }
        }
    }

    /**
     * retourne un nouveau monstre avec des pvs prédifinis
     * @param etage etage courant
     * @param m enum de monstre
     * @param pv les pvs du monstre
     * @return le monstre
     * @auhtor Quentin
     */
    protected static AbstractMonster getNewMonster(Etage etage, MonsterType m, int pv){
        switch (m){
            case BEE -> {
                return new Bee(etage,Procedure.getAccesibleRandomPosition(true,etage),"BEE", pv,5,15,20,900, Tools.PATH_CROSS, getBaseLvl());
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * retourne le level de base
     * @return int
     * @auhtor Quentin
     */
    private static int getBaseLvl(){
        return Start.getMap() == null ? 1 : Procedure.getRandomInt(3,0)+Start.getMap().getIndexCurrent()+1;
    }
}
