package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

import java.util.ArrayList;

public class Melee extends AbstractWeapon{

    /**
     * Constructeur de l'arme au corps a corps.
     * @param etage    Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom      Nom de l'arme
     * @param type     Type de l'arme
     * @param strength Puissance de l'arme
     * @param range    Portée de l'arme
     */
    public Melee(Etage etage, Position position, String nom, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, nom, type,strength, range);
    }

    @Override
    public void useItem(BasicPlayer player) {
        super.useItem(player);
        Position pos = player.getPosition();
        for (int i = 0; i < getRange(); i++) {
            pos = pos.somme(player.getDirection().getVecteur());
            Entity entity = player.getEtage().get(pos).getEntity();
            if(entity instanceof AbstractAlive && !(entity instanceof Marchand && ((Marchand) entity).getState() != Marchand.STATE.AGGRESSIVE)){
                entity.onContact(player);
                break;
            }
        }
        ArrayList<Position> zone = new ArrayList<>();
        zone.add(pos);
        Affichage.Projectile(player.getEtage(),zone,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_RED,"💫","+"));
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return switch (getRange()){
                case 1, 2 -> "🔧";
                case 3 -> "🔪";
                case 4 -> "🪓";
                default -> "🔗";
            };
        }
        else{
            return super.toString()+"m";
        }
    }
}
