package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

public class BasicPlayer extends AbstractAlive {
    private int MAX_EXP;
    private int CURRENT_EXP;
    private int MAX_PV;
    private long MovementCoolDown = System.currentTimeMillis();
    private int money;

    public BasicPlayer(int vision_radius, String nom, int pv, int force) {
        super(null, null, vision_radius, nom, pv, force, 1);
        MAX_EXP = 10;
        CURRENT_EXP = 0;
        MAX_PV = pv;
        money = 0;
    }

    public void updateEtage(Etage etage, Position position) {
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    public void addExp(int exp) {
        CURRENT_EXP += exp;
        if (CURRENT_EXP >= MAX_EXP) {
            MAX_EXP *= 2;
            CURRENT_EXP = 0;
            updatePV(MAX_PV / 2);
            MAX_PV *= 1.5;
            lvl++;
            TourManager.addMessage(Affichage.BRIGTH_CYAN + "Vous avez gagné un niveau");
        }
    }

    /**
     * Ajoute de l'argent au joueur
     *
     * @param m Montant de la monnaie à ajouter
     * @return true
     * @author Gillian
     */

    public boolean addMoney(int m) {
        money += m;
        return true;
    }

    /**
     * Enlève de l'argent au joueur
     *
     * @param m Montant de la monnaie à soustraire
     * @return true si possible, false sinon
     * @author Gillian
     */
    public boolean removeMoney(int m) {

        if (money - m < 0) {
            return false;
        } else {
            money -= m;
            return true;
        }

    }

    @Override
    public void death() {
        TourManager.addMessage(Affichage.BRIGTH_RED + getNom() + Affichage.BRIGTH_RED + " est mort.");
        Affichage.getMap();
        System.out.println("\nFin de la partie.");
        Start.end();
    }

    @Override
    public void updatePVMessage() {
    }

    public int getMAX_EXP() {
        return MAX_EXP;
    }

    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    public void moveLeft() {
        if (System.currentTimeMillis() - MovementCoolDown > 100) {
            move(getPosition().somme(-1, 0));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    public void moveRight() {
        if (System.currentTimeMillis() - MovementCoolDown > 100) {
            move(getPosition().somme(1, 0));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    public void moveUp() {
        if (System.currentTimeMillis() - MovementCoolDown > 100) {
            move(getPosition().somme(0, -1));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    public void moveDown() {
        if (System.currentTimeMillis() - MovementCoolDown > 100) {
            move(getPosition().somme(0, 1));
            MovementCoolDown = System.currentTimeMillis();
        }
    }

    @Override
    public int getPv() {
        return super.getPv();
    }

    public int getMAX_PV() {
        return MAX_PV;
    }

    /**
     * Retourne le montant de l'argent du joueur
     *
     * @return money
     * @author Gillian
     */
    public int getMoney() {
        return money;
    }

    @Override
    public int getForce() {
        return super.getForce() * lvl;
    }

    @Override
    public String toString() {
        if (System.getProperty("os.name").equals("Linux")) {
            return "\uD83E\uDD13";
        } else {
            return Affichage.GREEN + Affichage.BOLD + "@";
        }
    }
}
