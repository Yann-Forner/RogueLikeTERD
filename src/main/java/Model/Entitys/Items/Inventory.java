package Model.Entitys.Items;

import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Inventaire du joueur. C'est là que sont contenu les listes de potions, d'armes, de nourritures...
 * @author JP
 */
public class Inventory implements Serializable {

    private final int WEAPONS_LIMIT;
    private final int POTIONS_LIMIT;

    private final ArrayList<AbstractWeapon> weapons = new ArrayList<>();
    private final ArrayList<AbstractPotion> potions = new ArrayList<>();


    /**
     * Constructeur d'inventory. Initialise les listes.
     * @author JP
     */
    public Inventory(){
        WEAPONS_LIMIT = 15;
        POTIONS_LIMIT = 15;
    }

    /**
     * Drop l'item donné dans la case accessible la plus proche
     * @param player Joueur
     * @param item Item a jeter
     * @author JP
     */
    public void dropItem(Player player, AbstractItem item) {
        if(item != null) {
            Etage e = player.getEtage();
            int scanRange = 1;
            while(scanRange<2) {
                int playerPosX = player.getPosition().getX();
                int playerPosY = player.getPosition().getY();
                for(int x = playerPosX - scanRange; x <= playerPosX + scanRange; x++) {
                    for(int y = playerPosY - scanRange; y <= playerPosY + scanRange; y++) {
                        Cell c = e.get(x, y);
                        if(c.isAccesible() && c.getEntity() == null) {
                            //TODO enelever instanceof
                            if(item instanceof AbstractPotion){
                                potions.remove(item);
                            }
                            if(item instanceof AbstractWeapon){
                                weapons.remove(item);
                            }
                            e.addItem(WeaponFactory.getNewWeapon(e, WeaponFactory.WeaponType.WAND));
                            item.setPosition(new Position(x, y));
                            e.addItem(item);
                            return;
                        }
                    }
                }
                scanRange++;
            }
            TourManager.addMessage(Affichage.RED
                    + Affichage.BOLD
                    + "[ERREUR]"
                    + Affichage.RESET
                    + Affichage.RED
                    + " L'item ne peut pas etre jeté l'endroit est trop encombré.");
        }
    }

    /**
     * Utilise l'arme courante.
     * @author JP, Quentin
     */
    public void useCurrentWeapon(Player player) {
        AbstractWeapon weapon = getCurrentWeapon();
        if(weapon!=null){
            if(player.getClasse().canUse(weapon)){
                if(weapon.getCoutEndurence() <= player.getEndurence()){
                    weapon.useItem(player);
                }
                else{
                    TourManager.addMessage(Affichage.RED + Affichage.BOLD +
                            "[ERREUR]" +
                            Affichage.RESET + Affichage.RED +
                            " Pas assez d'energie.");
                }
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
     * Utilise la potion courante.
     * @author Quentin
     */
    public void useCurrentPotion(Player player) {
        AbstractPotion potion = getCurrentPotion();
        if(potion!=null) potion.useItem(player);
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     * @author JP
     */
    public void switchWeapons() {
        if(weapons.size() > 0) {
            AbstractWeapon first = weapons.remove(0);
            weapons.add(first);
        }
    }

    /**
     * Fais rouler la liste de potions afin de se déplacer vers le deuxième élement.
     * @author JP
     */
    public void switchPotions() {
        if(potions.size() > 0) {
            AbstractPotion first = potions.remove(0);
            potions.add(first);
        }
    }

    /**
     * Ajoute une potion a la liste de potions.
     * @param potion Potion à ajouter a la liste de potions
     * @return Si le joueur a pu recuperer la potion
     * @author JP, Quentin
     */
    public boolean addPotion(AbstractPotion potion){
        if(isPotionsFull()){
            dropItem(Start.getPlayer(),potion);
            return false;
        }
        else{
            potions.add(potion);
            return true;
        }
    }

    /**
     * Fais rouler la liste d'armes afin de se déplacer vers le deuxième élement.
     * @param arme Arme à ajouter a la liste d'armes
     * @return Si le joueur a pu recuperer la potion
     * @author JP, Quentin
     */
    public boolean addWeapon(AbstractWeapon arme){
        if(isWeaponsFull()){
            dropItem(Start.getPlayer(),arme);
            return false;
        }
        else{
            weapons.add(arme);
            return true;
        }
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

    /**
     * Renvoit la potion courante.
     * @return La potion equipée par le joueur
     * @author Quentin
     */
    public AbstractPotion getCurrentPotion(){
        return potions.size()>0 ? potions.get(0) : null;
    }



    /**
     * Permet de vendre une potion en ajoutant l'argent au joueur
     * @author Gillian
     */
    public void sellPotion ()
    {
        if(potions.size() > 0) {
            AbstractPotion first = potions.remove(0);
            Start.getPlayer().addMoney(first.getPrix());
        }

    }
    /**
     * Permet de vendre une arme en ajoutant l'argent au joueur
     * @author Gillian
     */
    public void sellWeapon ()
    {
        if(weapons.size() > 0) {
            AbstractWeapon first = weapons.remove(0);
            Start.getPlayer().addMoney(first.getPrix());
        }
    }

    /**
     * Retourne la limite d'armes
     * @return Limite d'armes
     * @author JP
     */

    public boolean isWeaponsFull() {
        return weapons.size()>=WEAPONS_LIMIT;
    }

    /**
     * Retourne la limite de potions
     * @return Limite de potions
     * @author JP
     */
    public boolean isPotionsFull() {
        return potions.size()>=POTIONS_LIMIT;
    }

}
