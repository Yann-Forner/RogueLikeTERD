package Model.Entitys.Player;

import Model.Entitys.AbstractAlive;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

/**
 * Classe de base du joueur
 * @author Quentin, Yann
 */
public class BasicPlayer extends AbstractAlive {
    private int MAX_EXP;
    private int CURRENT_EXP;
    private int MAX_PV;
    private long MovementCoolDown = System.currentTimeMillis();

    /**
     * Crée un joueur.
     * @param vision_radius Champs de vision
     * @param nom Nom
     * @param pv Points de vie
     * @param force Force
     * @author Quentin
     */
    public BasicPlayer(int vision_radius, String nom,int pv,int force) {
        super(null,null, vision_radius, nom, pv, force,1);
        MAX_EXP=10;
        CURRENT_EXP=0;
        MAX_PV=pv;
    }

    /**
     * Change l'etage et la position du joueur dans celui ci.
     * @param etage Etage
     * @param position Position
     * @author Quentin
     */
    public void updateEtage(Etage etage, Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    /**
     * Ajoute de l'experience au joueur.
     * Quend celle ci atteint un certain seuil, le joueur monte d'un niveau et elle rappart de 0.
     * @param exp Experience.
     * @author Quentin
     */
    public void addExp(int exp){
        CURRENT_EXP += exp;
        if(CURRENT_EXP>=MAX_EXP){
            MAX_EXP *= 2;
            CURRENT_EXP = 0;
            updatePV(MAX_PV/2);
            MAX_PV *= 1.5;
            lvl++;
            TourManager.addMessage(Affichage.BRIGTH_CYAN+"Vous avez gagné un niveau");
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
    public void updatePVMessage() {}

    /**
     * Renvoit l'experience necessaire pour monter de niveau.
     * @return Exp necessaire
     * @author Quentin
     */
    public int getMAX_EXP() {
        return MAX_EXP;
    }

    /**
     * Renvoit l'experience actuelle.
     * @return Exp actuelle
     * @author Quentin
     */
    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    /**
     * Deplace le joueur d'une cellule a sa gauche.
     * @author Yann
     */
    public void moveLeft() {
        if(System.currentTimeMillis()-MovementCoolDown>100){
            move(getPosition().somme(-1,0));
            MovementCoolDown=System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule a sa droite.
     * @author Yann
     */
    public void moveRight() {
        if(System.currentTimeMillis()-MovementCoolDown>100){
            move(getPosition().somme(1,0));
            MovementCoolDown=System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le haut.
     * @author Yann
     */
    public void moveUp() {
        if(System.currentTimeMillis()-MovementCoolDown>100){
            move(getPosition().somme(0,-1));
            MovementCoolDown=System.currentTimeMillis();
        }
    }

    /**
     * Deplace le joueur d'une cellule vers le bas.
     * @author Yann
     */
    public void moveDown() {
        if(System.currentTimeMillis()-MovementCoolDown>100){
            move(getPosition().somme(0,1));
            MovementCoolDown=System.currentTimeMillis();
        }
    }

    @Override
    public int getPv() {
        return super.getPv();
    }

    /**
     * Renvoit les pv maximum du joueur.
     * @return pv max
     * @author Quentin
     */
    public int getMAX_PV(){
        return MAX_PV;
    }

    @Override
    public int getForce() {
        return super.getForce()*lvl;
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83E\uDD13";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"@";
        }
    }
}
