package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractAlive;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class Bow extends Weapon {

    /**
     * Constructeur de l'arme
     *
     * @param etage Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom Nom de l'arme
     * @param weaponType Type de l'arme
     * @param strength Puissance de l'arme
     * @param range Portée de l'arme
     * @param durability Durabilitée de l'arme
     */
    public Bow(Etage etage, Position position, String nom, WeaponFactory.WeaponType weaponType, int strength, int range, int durability) {
        super(etage, position, nom, weaponType, strength, range, durability);
    }

    @Override
    public void useItem(BasicPlayer player) {
        int playerPositionX = player.getPosition().getX(), playerPositionY = player.getPosition().getY();

        for(Entity e : getEtage().getItems()) {
            if(e instanceof AbstractMonster) {
                int posEntityX = e.getPosition().getX();
                int posEntityY = e.getPosition().getY();

                if (Math.abs(posEntityX - playerPositionX) <= getRange() && Math.abs(posEntityY - playerPositionY) <= getRange()) {
                    ((AbstractMonster) e).updatePV(-getStrength());
                    Affichage.Projectile(player.getEtage(), player.getPosition(), e.getPosition(), new Cell.Style(Cell.Style.CellType.PROJECTILE));
                }
            }
        }
    }
}
