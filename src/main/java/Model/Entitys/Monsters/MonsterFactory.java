package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Procedure;
import Model.Utils.Start;
import Model.Utils.Tools;

/**
 * Factory qui crée les monstres
 * @author Yann,Quentin,JP
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
     * @author Yann,Quentin,JP
     */
    public static AbstractMonster getNewMonster(Etage etage, MonsterType m){
        return switch (m){
            case GHOST -> new Ghost(etage, Procedure.getAccesibleRandomPosition(true,etage),"GHOST", 10,3,10,10,700, Tools.PathType.GHOST,getBaseLvl());
            case RAT -> new Rat(etage, Procedure.getAccesibleRandomPosition(true,etage),"RAT",5,1,15,15,300, Tools.PathType.DIAG, getBaseLvl());
            case BEE -> new Bee(etage, Procedure.getAccesibleRandomPosition(true,etage),"BEE",20,5,15,20,900, Tools.PathType.CROSS,getBaseLvl());
            case ALIEN -> new Alien(etage, Procedure.getAccesibleRandomPosition(true,etage),"ALIEN",15,2,20,60,300, Tools.PathType.CROSS,getBaseLvl());
            case SNAIL -> new Snail(etage, Procedure.getAccesibleRandomPosition(true,etage),"SNAIL",10,5,60,60,600, Tools.PathType.CROSS,getBaseLvl());
            case BIRD -> new Bird(etage, Procedure.getAccesibleRandomPosition(true,etage),"BIRD",10,5,60,60,600, Tools.PathType.DIAG,getBaseLvl());
            case VOLCANO -> new Volcano(etage, Procedure.getAccesibleRandomPosition(true,etage),"VOLCANO",1,10,2,0,1000, Tools.PathType.CROSS,getBaseLvl());
            case SKULL -> new Skull(etage, Procedure.getAccesibleRandomPosition(true,etage),"SKULL",10,3,15,20,750, Tools.PathType.CROSS,getBaseLvl());
            case BIGMONSTER -> new BigMonster(etage,Procedure.getAccesibleRandomPosition(true,etage),"BigMonster",10,18,15,20,900, Tools.PathType.CROSS,getBaseLvl()+5);
            case SNAKE -> new Snake(etage,Procedure.getAccesibleRandomPosition(true,etage),"Snake",10,1,15,20,900, Tools.PathType.NOMOBS,getBaseLvl()+5,10);
            case INVOQUEUR -> new Invoqueur(etage,Procedure.getAccesibleRandomPosition(true,etage),"INVOQUEUR",25,5,3,20,800, Tools.PathType.CROSS,getBaseLvl()+5);
            case MARCHAND -> new Marchand(etage,Procedure.getAccesibleRandomPosition(true,etage),"MARCHAND",50,8,15,20,1000, Tools.PathType.CROSS,getBaseLvl()+5, Marchand.STATE.NOTVISITED);
            case ZOMBIE -> new Zombie(etage, Procedure.getAccesibleRandomPosition(true,etage),"ZOMBIE",25,5,5,30,1500, Tools.PathType.CROSS,getBaseLvl());
        };
    }

    /**
     * Retourne le level de base
     * @return int
     * @author Quentin
     */
    private static int getBaseLvl(){
        return Start.getMap() == null ? 1 : Procedure.getRandomInt(3,0)+Start.getMap().getIndexCurrent()+1;
    }
}
