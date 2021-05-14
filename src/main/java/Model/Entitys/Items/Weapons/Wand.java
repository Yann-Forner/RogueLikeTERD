package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Baguette de magicien.
 * @author Quentin
 */
public class Wand extends AbstractWeapon {

    /**
     * Constructeur de la baguette
     * @param etage      Etage où se situe l'arme
     * @param position   Position de l'arme
     * @param type     Type de l'arme
     * @param strength   Puissance de l'arme
     * @param range      Portée de l'arme
     */
    public Wand(Etage etage, Position position, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, null, type, strength, range);
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        ArrayList<Position> zone = new ArrayList<>();
        int distance = getRange();
        Position current = Objects.requireNonNull(player).getPosition().somme(player.getDirection().getVecteur());
        while(player.getEtage().get(current).getType() != Cell.Style.CellType.BORDER && distance!=0){
            zone.add(current);
            current = current.somme(player.getDirection().getVecteur());
            distance--;
        }
        for(Position p : zone){
            Entity entity = player.getEtage().get(p).getEntity();
            if(entity instanceof AbstractAlive){
                if (entity instanceof Marchand && ((Marchand) entity).getState() != Marchand.STATE.AGGRESSIVE){
                    continue;
                }
                entity.onContact(player);
            }
        }
        Affichage.Projectile(player.getEtage(),zone,getMagicStyle());
    }

    /**
     * Renvoit un style de magie selon la portée.
     * @return Style
     * @author Quentin
     */
    private Cell.Style getMagicStyle(){
        return switch (getRange()){
            case 1, 2, 3 -> new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_RED,"💥","+");
            case 4, 5, 6 -> new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_YELLOW,"🌟","*");
            case 7, 8, 9 -> new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_GREEN,"🌀","x");
            default -> new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_BLUE,"💢","¤");
        };
    }

    @Override
    public String getNom() {
        return switch (getRange()){
            case 1, 2, 3 -> "Balait";
            case 4, 5, 6 -> "Os";
            case 7, 8, 9 -> "Baguette";
            default -> "Canne";
        };
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return switch (getRange()){
                case 1, 2, 3 -> "🧹";
                case 4, 5, 6 -> "🦴";
                case 7, 8, 9 -> "🥢";
                default -> "🦯";
            };
        }
        else{
            return super.toString()+"w";
        }
    }
}
