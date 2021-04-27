package Model.Entitys.Items.Weapons;

import Model.Entitys.AbstractItem;
import Model.Entitys.Entity;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

public class Weapon extends AbstractItem {

    private WeaponFactory.WeaponType weaponType;

    private int strength;
    private int range;
    private int durability;

    public Weapon(Etage etage, Position position, String nom, WeaponFactory.WeaponType weaponType) {
        super(etage, position, nom);
        this.weaponType = weaponType;
    }

    @Override
    public void onContact(Entity e) {
        if(!(e instanceof AbstractMonster)){
            BasicPlayer player = (BasicPlayer) e;
            TourManager.addMessage(Affichage.BLUE + player.getNom() + " a ramassé "+ Affichage.BRIGTH_BLUE + getNom() + Affichage.BLUE + ".");
            player.getInventory().addWeapon(this);
            player.getEtage().removeItem(this);
        }
    }

    @Override
    public void useItem(BasicPlayer player) {
        player.setForce(strength);
        switch(weaponType) {
            case BOW -> {
                int playerPositionX = player.getPosition().getX(), playerPositionY = player.getPosition().getY();

                for(int xScan = playerPositionX - range; xScan < playerPositionX + range; xScan++) {
                    for(int yScan = playerPositionY - range; yScan < playerPositionY + range; yScan++) {
                        if(xScan != playerPositionX && yScan != playerPositionY) {
                            Entity e = player.getEtage().get(xScan, yScan).getEntity();
                            if(e instanceof AbstractMonster) {
                                ((AbstractMonster) e).updatePV(-strength);
                                // Affichage.Projectile(player.getEtage(), player.getPosition(), e.getPosition(), );
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}