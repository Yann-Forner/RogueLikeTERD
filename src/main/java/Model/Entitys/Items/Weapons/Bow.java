package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Tools;

import java.util.ArrayList;

public class Bow extends AbstractWeapon {

    /**
     * Constructeur de l'arc.
     * @param etage Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom Nom de l'arme
     * @param type Type de l'arme
     * @param strength Puissance de l'arme
     * @param range Portée de l'arme
     */
    public Bow(Etage etage, Position position, String nom, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, nom, type,strength, range);
    }

    @Override
    public void useItem(BasicPlayer player) {
        Position position = player.getPosition();
        int range = getRange();
        for (int x = position.getX() - range; x < position.getX() + range * 2 - 1; x++) {
            for (int y = position.getY() - range; y < position.getY() + range * 2 - 1; y++) {
                if(x>=0 && x<getEtage().getWidth() && y>=0 && y<getEtage().getHeigth()){
                    Position pos = new Position(x, y);
                    if (position.Distance(pos) <= range) {
                        Entity entity = getEtage().get(pos).getEntity();
                        if (entity instanceof AbstractAlive && entity!=player) {
                            if (entity instanceof Marchand && ((Marchand) entity).getState() == Marchand.STATE.AGGRESSIVE){
                                continue;
                            }
                            ArrayList<Position> zone = Tools.getLigne(entity.getPosition(),player.getPosition());
                            boolean noBorder = true;
                            for(Position p : zone){
                                if(getEtage().get(p).getType().equals(Cell.Style.CellType.BORDER)){
                                    noBorder = false;
                                }
                            }
                            if(noBorder) {
                                entity.onContact(player);
                                Affichage.Projectile(getEtage(), zone,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_BLUE,"🌀","x"));
                                x=y=1000;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "🏹";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"S";
        }
    }
}
