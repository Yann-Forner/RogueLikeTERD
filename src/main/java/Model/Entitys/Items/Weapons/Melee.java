package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Sound;

import java.util.ArrayList;

public class Melee extends AbstractWeapon{

    /**
     * Constructeur de l'arme au corps a corps.
     * @param etage    Etage où se situe l'arme
     * @param position Position de l'arme
     * @param type     Type de l'arme
     * @param strength Puissance de l'arme
     * @param range    Portée de l'arme
     */
    public Melee(Etage etage, Position position, WeaponFactory.WeaponType type, int strength, int range) {
        this(etage, position, type, strength, range, 0);
    }

    /**
     * Constructeur de l'arme au corps a corps.
     * @param etage    Etage où se situe l'arme
     * @param position Position de l'arme
     * @param type     Type de l'arme
     * @param strength Puissance de l'arme
     * @param range    Portée de l'arme
     * @param prix     Prix de l'arme
     */
    public Melee(Etage etage, Position position, WeaponFactory.WeaponType type, int strength, int range, int prix) {
        super(etage, position,"Epée", type,strength, range, prix);
    }


    @Override
    public void useItemMessage() {
        Sound.playAudio(Sound.Sons.EPEEDAMAGE,0);
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        Position pos = player.getPosition();
        for (int i = 0; i < getRange(); i++) {
            pos = pos.somme(player.getDirection().getVecteur());
            Entity entity = player.getEtage().get(pos).getEntity();
            if(entity instanceof AbstractAlive && !(entity instanceof Marchand && Marchand.getState() != Marchand.STATE.AGGRESSIVE)){
                entity.onContact(player);
                break;
            }
        }
        ArrayList<Position> zone = new ArrayList<>();
        zone.add(pos);
        Affichage.Projectile(player.getEtage(),zone,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_RED,"💫","+"));
        useItemMessage();
    }

    @Override
    public String getNom() {
        return switch (getRange()){
            case 1, 2 -> "Clé a molette";
            case 3 -> "Couteau";
            case 4 -> "Hache";
            default -> "Chaine";
        };
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
