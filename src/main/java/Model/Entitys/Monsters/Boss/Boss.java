package Model.Entitys.Monsters.Boss;

import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;
import Model.Utils.TourManager;

public abstract class Boss extends AbstractMonster {

    /**
     * Intitialise un monstre avec des stats de bases.
     *
     * @param m              Son Etage
     * @param pos            Sa Position
     * @param nom            Son Nom
     * @param pv             Ses PV de base
     * @param force          Sa Force de base
     * @param vision_radius  Sonc champs de vision
     * @param agro           La distance maximal sur laquelle il va poursuivre le joueur
     * @param update_rate_ms Sa vitesse
     * @param path_type      Son type de deplacement (pour Astar)
     * @param lvl            Son niveau
     * @author Quentin
     */
    protected Boss(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
    }

    @Override
    public void death() {
        super.death();
        TourManager.addKillBoss();
        Procedure.setRandomUPnDOWN(getEtage());
    }

    @Override
    protected void dropOnDeath() {
        int rand = Procedure.getRandomInt(3, 0);

        for(int i = 0; i < rand; i++) {
            int randWeapon = Procedure.getRandomInt(2, 0);
            if (randWeapon == 0)
                getInventory().dropItem(this, WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.SWORD));
            else if (rand == 1)
                getInventory().dropItem(this, WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.BOW));
            else
                getInventory().dropItem(this, WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.WAND));
        }
        super.dropOnDeath();
    }
}
