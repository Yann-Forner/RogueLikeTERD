package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.Marchand;
import Model.Entitys.Monsters.Volcano;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Sound;
import Model.Utils.Tools;

import java.util.ArrayList;

public class Bow extends AbstractWeapon {

    /**
     * Constructeur de l'arc.
     * @param etage Etage où se situe l'arme
     * @param position Position de l'arme
     * @param type Type de l'arme
     * @param strength Puissance de l'arme
     * @param range Portée de l'arme
     * @param prix Prix de l'arme
     * @author Quentin
     */
    public Bow(Etage etage, Position position, WeaponFactory.WeaponType type, int strength, int range, int prix) {
        super(etage, position,"Arc", type,strength, range, prix);
    }

    @Override
    public void useItemMessage() {
        Sound.playAudio(Sound.Sons.BOWDAMAGE,0);
    }

    @Override
    public void useItem(Player player) {
        Position position = player.getPosition();
        int range = getRange();
        Entity cible = null;
        int distanceCible = 1000;
        ArrayList<Position> zone = new ArrayList<>();
        for (int x = position.getX() - range; x < position.getX() + range * 2 - 1; x++) {
            for (int y = position.getY() - range; y < position.getY() + range * 2 - 1; y++) {
                if(x>=0 && x<player.getEtage().getWidth() && y>=0 && y<player.getEtage().getHeigth()){
                    Position pos = new Position(x, y);
                    if (position.Distance(pos) <= range) {
                        Entity entity = player.getEtage().get(pos).getEntity();
                        if (entity instanceof AbstractAlive && entity!=player) {
                            if (entity instanceof Marchand && Marchand.getState() != Marchand.STATE.AGGRESSIVE || entity instanceof Volcano){
                                continue;
                            }
                            if (pos.Distance(position) < distanceCible) {
                                zone = Tools.getLigne(entity.getPosition(),player.getPosition());
                                boolean noBorder = true;
                                for(Position p : zone){
                                    if(player.getEtage().get(p).getType().equals(Cell.Style.CellType.BORDER)){
                                        noBorder = false;
                                    }
                                }
                                if(noBorder) {
                                    distanceCible = (int) pos.Distance(position);
                                    cible = entity;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(cible != null){
            super.useItem(player);
            cible.onContact(player);
            Affichage.Projectile(player.getEtage(), zone,new Cell.Style(Cell.Style.CellType.PROJECTILE,Affichage.BRIGTH_BLUE,"📍","x"));
            useItemMessage();
        }
    }

    @Override
    public String getNom() {
        return switch (getRange()){
            case 1,2,3,4 -> "Canne a pêche";
            case 5,6,7,8 -> "Arc";
            case 9,10,11,12 -> "Tridant";
            default -> "Revolver";
        };
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return switch (getRange()){
                case 1,2,3,4 -> "🎣";
                case 5,6,7,8 -> "🏹";
                case 9,10,11,12 -> "🔱";
                default -> "🔫";
            };
        }
        else{
            return super.toString()+"b";
        }
    }
}
