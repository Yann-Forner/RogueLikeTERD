package Model.Entitys.Player.Classes;

import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.BasicPlayer;

import java.io.Serializable;

/**
 * Classe du joueur.
 * @author Quentin
 */
public abstract class AbstractClass implements Serializable {

    public AbstractClass(){ }

    /**
     * Renvoit le champs de vision de cette classe.
     * @return Champs de vision
     * @author Quentin
     */
    public abstract int getVisionRadius();

    /**
     * Renvoit les points de bie de base de cette classe.
     * @return Points de vie
     * @author Quentin
     */
    public abstract int getBasePV();

    /**
     * Renvoit la force de base de cette classe.
     * @return Force
     * @author Quentin
     */
    public abstract int getBaseForce();

    /**
     * Renvoit la vitesse de base de cette classe.
     * Plus la valeur est basse et plus le joueur peut se deplacer vite.
     * @return Temps entre deux mouvements en ms.
     * @author Quentin
     */
    public abstract int getSpeed();

    /**
     * Defini l'equipement de base de la classe.
     * @param player joueur
     * @author Quentin
     */
    public abstract void setBaseItems(BasicPlayer player);


    public abstract boolean canUse(AbstractWeapon weapon);

    /**
     * Renvoit le nom de la classe.
     * @return Nom de la classe
     * @author Quentin
     */
    public abstract String getNom();
}
