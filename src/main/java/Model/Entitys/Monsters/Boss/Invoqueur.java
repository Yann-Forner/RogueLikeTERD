package Model.Entitys.Monsters.Boss;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Boss qui tire des lasers et invoque des rat des qu'il prend des degats.
 * @author Quentin
 */
public class Invoqueur extends Boss {
    private final double portee_min;
    private double portee;
    private int direction = 0;

    /**
     * Class du boss invoqueur.
     * @param m Etage
     * @param pos Position
     * @param nom Nom
     * @param pv Points de vie
     * @param force Force
     * @param portee_min La portée minimal de son laser
     * @param portee_max La portée maximal de son laser
     * @param update_rate Sa vitesses
     * @param pathCross Son type de deplacement
     * @param lvl Son niveau
     * @author Quentin
     */
    public Invoqueur(Etage m, Position pos, String nom, int pv, int force, double portee_min , int portee_max, int update_rate, Tools.PathType pathCross, int lvl) {
        super(m, pos, nom, pv, force, portee_min, portee_max, update_rate, pathCross, lvl);
        this.portee_min = portee_min;
        portee = portee_min;
    }

    @Override
    public void updateMonster() {
        Player player = Start.getPlayer();
        if(Objects.requireNonNull(player).getPosition().Distance(getPosition()) != 1) {
            move(nextPosition());
        }
        portee = portee < Agro ? portee * 1.5 : portee_min;
        int ceil = (int)Math.ceil(portee);
        ArrayList<Position> zone = switch (direction%8){
            case 0 -> Tools.getLigne(getPosition(), getPosition().somme(0,-ceil));
            case 1 -> Tools.getLigne(getPosition(), getPosition().somme(ceil,-ceil));
            case 2 -> Tools.getLigne(getPosition(), getPosition().somme(ceil,0));
            case 3 -> Tools.getLigne(getPosition(), getPosition().somme(ceil,ceil));
            case 4 -> Tools.getLigne(getPosition(), getPosition().somme(0,ceil));
            case 5 -> Tools.getLigne(getPosition(), getPosition().somme(-ceil,ceil));
            case 6 -> Tools.getLigne(getPosition(), getPosition().somme(-ceil,0));
            case 7 -> Tools.getLigne(getPosition(), getPosition().somme(-ceil,-ceil));
            default -> new ArrayList<>();
        };
        direction++;
        ArrayList<Position> effet = new ArrayList<>();
        for(Position p : zone){
            Cell c = getEtage().get(p);
            if(c.getType().equals(Cell.Style.CellType.BORDER)){
                break;
            }
            effet.add(p);
            Entity entity = c.getEntity();
            if(entity instanceof AbstractAlive && entity != this){
                entity.onContact(this);
            }
        }
        Affichage.Projectile(getEtage(),effet,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_RED,"🔆","+"));
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        Etage etage = getEtage();
        etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.RAT)));
        return super.updatePV(pv, limited);
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDDFD";
        }
        else{
            return super.toString() + "I";
        }
    }
}
