package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;

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
     * @param nom        Nom de l'arme
     * @param type     Type de l'arme
     * @param strength   Puissance de l'arme
     * @param range      Portée de l'arme
     */
    public Wand(Etage etage, Position position, String nom, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, nom, type, strength, range);
    }

    @Override
    public void useItem(BasicPlayer player) {
        ArrayList<Position> zone = new ArrayList<>();
        int distance = getRange();
        Position current = Objects.requireNonNull(Start.getPlayer()).getPosition().somme(Start.getPlayer().getDirection().getVecteur());
        while(player.getEtage().get(current).getType() != Cell.Style.CellType.BORDER && distance!=0){
            zone.add(current);
            current = current.somme(Start.getPlayer().getDirection().getVecteur());
            distance--;
        }
        for(Position p : zone){
            Entity entity = Start.getPlayer().getEtage().get(p).getEntity();
            if(entity instanceof AbstractAlive){
                entity.onContact(Start.getPlayer());
            }
        }
        Affichage.Projectile(player.getEtage(),zone,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_RED,"💥","+"));
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"🧹";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"S";
        }
    }
}
