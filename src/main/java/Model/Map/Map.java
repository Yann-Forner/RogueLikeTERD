package Model.Map;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage_Strategy.BossEtageStategy;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Etage_Strategy.ReposEtageStrategy;
import Model.Map.Etage_Strategy.TrapEtageStrategy;
import Model.Utils.Start;
import Model.Utils.Position;
import Model.Utils.Procedure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Espace de jeu qui se compose de plusieurs etages.
 * @author Quentin
 */
public class Map implements Serializable {
    private final ArrayList<Etage> etages = new ArrayList<>();
    public final int MapWidth = 40;
    public final int MapHeigth = 40;
    private boolean inTemporaryEtage=false;

    /**
     * Genere la Map.
     * @param player Joueur
     * @author Quentin
     */
    public Map(BasicPlayer player){
        Etage etage = new Etage(MapWidth, MapHeigth, EtageStrategy.getRandomStrategy());
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        etage.get(pos).setEntity(player);
        player.setEtage(etage);
        player.setPosition(pos);
    }

    /**
     * Renvoit l'etage courent.
     * @return Etage
     * @author Quentin
     */
    public Etage getCurrent(){
        return Objects.requireNonNull(Start.getPlayer()).getEtage();
    }

    /**
     * Renvoit le numero de l'etage courent.
     * @return index
     * @author Quentin
     */
    public int getIndexCurrent(){
        return etages.indexOf(getCurrent());
    }

    /**
     * Permet de descendre la l'etage suivant.
     * @author Quentin
     */
    public void DOWN(){
        Etage etage;
        int currentIndex = getIndexCurrent();
        if(currentIndex == etages.size()-1){
            int numEtage = currentIndex + 2;
            EtageStrategy strategy;
            if(numEtage%10==0){
                strategy = new BossEtageStategy();
            }
            else if(numEtage%5==0){
                strategy = new ReposEtageStrategy();
            }
            else{
                strategy = EtageStrategy.getRandomStrategy();
            }
            etage=new Etage(MapWidth,MapHeigth, strategy);
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
    }

    /**
     * Permet de remonter a l'etage precedent.
     * @author Quentin
     */
    public void UP(){
        //TODO enlever le up si premier etage
        if(inTemporaryEtage){
            Etage etage=etages.get(etages.size()-1);
            Position pos = Procedure.getAccesibleRandomPosition(true,etage);
            Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
            inTemporaryEtage=false;
        }
        else{
            int currentIndex = getIndexCurrent();
            if(currentIndex!=0){
                Etage etage=etages.get(currentIndex-1);
                Position pos = Procedure.getAccesibleRandomPosition(true,etage);
                Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
            }
        }
    }

    /**
     * Etage piege dans lequel le joueur peut arriver par malchance.
     * @author Quentin
     */
    public void TRAP_ROOM(){
        //TODO revenir a l'etage de depart en remontant et non le dernier index de l'arraylist
        //TODO rework
        Etage etage = new Etage(MapWidth,MapHeigth,new TrapEtageStrategy());
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
        inTemporaryEtage=true;
    }

    /**
     * Renvoit la liste des etages.
     * @return ArrayList<Etage>
     * @author Quentin
     */
    public ArrayList<Etage> getEtages(){
        return etages;
    }

    @Override
    public String toString() {
        return getCurrent().toString();
    }
}
