package Model.Entitys.Items.Weapons;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Objet représentant les armes, contenant leurs caractéristiques (puissance, portée, type...)
 * @author JP
 */
public class Weapon extends AbstractItem {

    private WeaponFactory.WeaponType weaponType;

    private int strength;
    private int range;
    private int durability;

    /**
     * Constructeur de l'arme
     * @param etage Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom Nom de l'arme
     * @param weaponType Type de l'arme
     */
    public Weapon(Etage etage, Position position, String nom, WeaponFactory.WeaponType weaponType, int strength, int range, int durability) {
        super(etage, position, nom);
        this.weaponType = weaponType;
        this.strength = strength;
        this.range = range;
        this.durability = durability;
    }

    /**
     * Ajoute l'arme a l'inventaire du joueur, si c'est un joueur
     * @param e Entité qui entre en contact de l'arme
     */
    @Override
    public void onContact(Entity e) {
        if(!(e instanceof AbstractMonster)){
            BasicPlayer player = (BasicPlayer) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé "+ Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
            player.getInventory().addWeapon(this);
            player.getEtage().removeItem(this);
        }
    }

    /**
     * Utilise l'arme
     * @param player Joueur possédant l'arme
     */
    @Override
    public void useItem(BasicPlayer player) {
        player.setForce(strength);
        System.out.println(weaponType);
        switch(weaponType) {
            case BOW -> {
                int playerPositionX = player.getPosition().getX(), playerPositionY = player.getPosition().getY();

                for(int xScan = playerPositionX - range; xScan < playerPositionX + range; xScan++) {
                    for(int yScan = playerPositionY - range; yScan < playerPositionY + range; yScan++) {

                        if(xScan != playerPositionX && yScan != playerPositionY
                                && xScan < getEtage().getWidth() && yScan < getEtage().getHeigth()
                                    && xScan > 0 && yScan > 0) {

                            Entity e = player.getEtage().get(xScan, yScan).getEntity();
                            if(e != null && e instanceof AbstractMonster) {
                                ((AbstractMonster) e).updatePV(-strength);
                                Affichage.Projectile(player.getEtage(), player.getPosition(), e.getPosition(), new Cell.Style(Cell.Style.CellType.PROJECTILE));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return weaponType.getValue();
    }

    /**
     *
     * @return Retourne la puissance de l'arme
     */
    public int getStrength() {
        return strength;
    }

    /**
     *
     * @param strength Force a redéfinir
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     *
     * @return Retourne la portée de l'arme
     */
    public int getRange() {
        return range;
    }

    /**
     *
     * @param range Portée à redéfinir
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     *
     * @return Retourne la durabilitée de l'arme
     */
    public int getDurability() {
        return durability;
    }

    /**
     *
     * @param durability Durabilitée a redéfinir
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }
}