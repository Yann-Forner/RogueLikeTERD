package Model.Entitys.Items.Weapons;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.TourManager;

/**
 * Objet représentant les armes, contenant leurs caractéristiques (puissance, portée, type...)
 * @author JP, Quentin
 */
public abstract class AbstractWeapon extends AbstractItem {
    private final int strength;
    private final int range;
    private final int coutEndurence;
    private final WeaponFactory.WeaponType type;

    /**
     * Constructeur de l'arme
     * @param etage Etage où se situe l'arme
     * @param position Position de l'arme
     * @param nom Nom de l'arme
     * @param strength Puissance de l'arme
     * @param range Portée de l'arme
     * @author JP
     */
    public AbstractWeapon(Etage etage, Position position, String nom, WeaponFactory.WeaponType type, int strength, int range) {
        super(etage, position, nom);
        this.type = type;
        this.strength = strength;
        this.range = range;
        this.coutEndurence = 7;
    }

    /**
     * Ajoute l'arme a l'inventaire du joueur, si c'est un joueur
     * @param e Entité qui entre en contact de l'arme
     * @author JP
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

    @Override
    public void useItem(BasicPlayer player) {
        player.updateEndurence(-getCoutEndurence());
    }

    /**
     * Renvoit le type de l'arme.
     * @return Type
     * @author Quentin
     */
    public WeaponFactory.WeaponType getType(){
        return type;
    }

    /**
     * Renvoit la force de l'arme.
     * @return Retourne la puissance de l'arme
     * @author JP
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Renvoit la portéé de l'arme
     * @return Retourne la portée de l'arme
     * @author JP
     */
    public int getRange() {
        return range;
    }

    /**
     * Renvoit le cout en endurence pour utiliser l'arme.
     * @return Cout en endurence
     * @author Quentin
     */
    public int getCoutEndurence(){
        return coutEndurence;
    }

}