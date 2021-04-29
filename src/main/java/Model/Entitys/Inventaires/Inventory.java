package Model.Entitys.Inventaires;

import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.BasicPlayer;
import Model.Utils.Affichage;
import Model.Utils.TourManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Inventaire du joueur. C'est là que sont contenu les listes de potions, d'armes, de nourritures...
 * @author JP
 */
public class Inventory implements Serializable {
    private final ArrayList<AbstractWeapon> weapons = new ArrayList<>();
    private final ArrayList<AbstractPotion> potions = new ArrayList<>();


    /**
     * Constructeur d'inventory. Initialise les listes.
     * @author JP
     */
    public Inventory(){ }

    /**
     * Utilise l'arme courante
     */
    public void useCurrentWeapon(BasicPlayer player) {
        if(weapons.size() > 0){
            AbstractWeapon weapon = weapons.get(0);
            if(player.getClasse().canUse(weapon)){
                weapon.useItem(player);
            }
            else{
                TourManager.addMessage(Affichage.RED + Affichage.BOLD +
                        "[ERREUR]" +
                        Affichage.RESET + Affichage.RED +
                        " Le type de l'arme est incompatible avec la classe du joueur.");
            }
        }
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     * @author JP
     */
    public void switchWeapons() {
        AbstractWeapon first = weapons.remove(0);
        weapons.add(first);
    }

    /**
     * Fais rouler la liste de potions afin de se déplacer vers le deuxième élement.
     * @author JP
     */
    public void switchPotions() {
        AbstractPotion first = potions.remove(0);
        potions.add(first);
    }

    /**
     * Ajoute une potion a la liste de potions.
     * @param potion Potion à ajouter a la liste de potions
     * @author JP
     */
    public void addPotion(AbstractPotion potion){
        potions.add(potion);
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     * @param arme Arme à ajouter a la liste d'armes
     * @author JP
     */
    public void addWeapon(AbstractWeapon arme){
        weapons.add(arme);
    }

    /**
     * Retourne la liste de potions
     * @return Retourne la liste de potions
     * @author JP
     */
    public ArrayList<AbstractPotion> getPotions() {
        return potions;
    }

    /**
     * Retourne la liste d'armes
     * @return Retourne la liste d'armes
     * @author JP
     */
    public ArrayList<AbstractWeapon> getWeapons() {
        return weapons;
    }

    /**
     * Renvoit l'arme courrante.
     * @return L'arme equipé par le joueur
     * @author Quentin
     */
    public AbstractWeapon getCurrentWeapon(){
        return weapons.size()>0 ? weapons.get(0) : null;
    }

}
