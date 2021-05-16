package Model.Map;

import Model.Entitys.Player.Player;
import Model.Map.Etage_Strategy.*;
import Model.Utils.Affichage;
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
    public int indexLastEtage = 0;
    public final int MapWidth = 40;
    public final int MapHeigth = 40;
    private boolean inTemporaryEtage = false;
    private boolean monterDescendre = false;

    /**
     * Genere la Map.
     * @param player Joueur
     * @author Quentin
     */
    public Map(Player player){
        Etage etage = new Etage(MapWidth, MapHeigth, EtageStrategy.getRandomStrategy(),true);
        etages.add(etage);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        etage.get(pos).setEntity(player);
        player.setEtage(etage);
        player.setPosition(pos);
        player.getClasse().setBaseItems(player);
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
        indexLastEtage++;
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
            etage = new Etage(MapWidth,MapHeigth, strategy,false);
            etages.add(etage);
        }
        else{
            etage = etages.get(currentIndex + 1);
        }
        Position pos = etage.getUp();
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
        monterDescendre = true;
    }

    /**
     * Permet de remonter a l'etage precedent.
     * @author Quentin
     */
    public void UP(){
        Etage etage;
        if(inTemporaryEtage){
            etage = etages.get(indexLastEtage);
            inTemporaryEtage=false;
            Affichage.setOmbre(Affichage.Shadow.NONE);
        }
        else{
            indexLastEtage--;
            etage = etages.get(getIndexCurrent()-1);
        }
        Position pos = etage.getDown();
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
        monterDescendre = true;
    }

    /**
     * Etage piege dans lequel le joueur peut arriver par malchance.
     * @author Quentin
     */
    public void TRAP_ROOM(){
        Etage etage = new Etage(MapWidth,MapHeigth,new TrapEtageStrategy(),false);
        Position pos = Procedure.getAccesibleRandomPosition(true,etage);
        Objects.requireNonNull(Start.getPlayer()).updateEtage(etage,pos);
        inTemporaryEtage = true;
        Affichage.setOmbre(Affichage.Shadow.RAY);
    }

    /**
     * Renvoit la liste des etages.
     * @return ArrayList<Etage>
     * @author Quentin
     */
    public ArrayList<Etage> getEtages(){
        return etages;
    }

    /**
     * Renvoit si le joueur viens de monter ou descendre d'un etage.
     * @return boolean
     * @author Quentin
     */
    public boolean getMonterDescendre(){
        return monterDescendre;
    }

    /**
     * Defini si le joueur viens de monter ou descendre d'un etage.
     * @param monterDescendre boolean
     * @author Quentin
     */
    public void setMonterDescendre(boolean monterDescendre) {
        this.monterDescendre = monterDescendre;
    }

    @Override
    public String toString() {
        return getCurrent().toString();
    }
}
