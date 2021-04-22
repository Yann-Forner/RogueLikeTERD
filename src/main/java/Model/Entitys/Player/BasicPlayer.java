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

    public BasicPlayer(int vision_radius, String nom,int pv,int force) {
        super(null,null, vision_radius, nom, pv, force,1);
        MAX_EXP=10;
        CURRENT_EXP=0;
        MAX_PV=pv;
    }

    public void updateEtage(Etage etage, Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

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

    public int getMAX_EXP() {
        return MAX_EXP;
    }

    public int getCURRENT_EXP() {
        return CURRENT_EXP;
    }

    public void moveLeft() {
        move(getPosition().somme(-1,0));
    }

    public void moveRight() {
        move(getPosition().somme(1,0));
    }

    public void moveUp() {
        move(getPosition().somme(0,-1));
    }

    public void moveDown() {
        move(getPosition().somme(0,1));
    }

    @Override
    public int getPv() {
        return super.getPv();
    }

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
