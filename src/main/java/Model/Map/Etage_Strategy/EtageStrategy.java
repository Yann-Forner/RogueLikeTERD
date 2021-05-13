package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;
import java.util.Objects;
/**
 * Classe abstraite définissant le fonctionnement d'un étage.
 * @author Yann,Quentin
 */
public abstract class EtageStrategy {
    /**
     * Genere les composants de l'étage
     * @param etage etage courant
     * @param etageDepart change le comportement si c'est le premier etage de la map
     * @author Quentin,Yann
     */
    public abstract void composeEtage(Etage etage, boolean etageDepart);

    /**
     * Renvoit le nombre maximal de rooms dans un etage.
     * @return int
     * @author Quentin
     */
    public abstract int getNbrMaxRoom();

    /**
     * Retourne aléatoirement une Strategie d'étage.
     * @return EtageStrategy
     * @author Quentin,Yann
     */
    public static EtageStrategy getRandomStrategy(){
        return switch (Procedure.getRandomInt(9,0)){
            case 0, 1, 2 -> new NormalEtageStrategy();
            case 3, 4, 5, 6, 7 -> new DonjonStrategy();
            default -> new CircleEtageStrategy();
        };
    }

    /**
     * Trace les chemins.
     * @param etage etage courant
     * @param style_fusion style
     * @author Quentin,Yann
     */
    protected void EtageFusion(Etage etage, Cell.Style style_fusion){
        for (int i = 0; i < etage.getRooms().size()-1; i++) {
            Room r1 = etage.getRooms().get(i);
            Room r2 = etage.getRooms().get(i+1);
            Tools.ligne(etage, new Position(r1.getWidth()/2,r1.getHeigth()/2).somme(r1.getAbsolutePos()), new Position(r2.getWidth()/2,r2.getHeigth()/2).somme(r2.getAbsolutePos()), style_fusion, Procedure.getRandomInt(6,0));
        }
        cleanFusion(etage,style_fusion);
    }

    /**
     * Ajoute les murs, et supprime les murs inutiles d'un étage.
     * @param etage etage courant
     * @param style_fusion style
     * @author Quentin
     */
    protected void cleanFusion(Etage etage,Cell.Style style_fusion){
        //Ajout des murs aux chemins
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos = new Position(x, y);
                Cell.Style.CellType type = etage.get(pos).getType();
                if (type.equals(Cell.Style.CellType.VOID)) {
                    ArrayList<Position> voisins = pos.voisins(etage);
                    for (Position p : voisins) {
                        if (!etage.get(p).getType().equals(Cell.Style.CellType.BORDER) && !etage.get(p).getType().equals(Cell.Style.CellType.VOID)){
                            etage.get(x,y).updateCell(false, new Cell.Style(Cell.Style.CellType.BORDER));
                        }
                    }
                }
                if(type.equals(Cell.Style.CellType.NORMAL) && (pos.getX() == 0 || pos.getY() == 0 || pos.getX() == etage.getWidth()-1 || pos.getY() == etage.getHeigth()-1)){
                    etage.get(pos).updateCell(false, new Cell.Style(Cell.Style.CellType.BORDER));
                }
            }
        }
        //Suppression des murs inutiles
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos = new Position(x, y);
                if(etage.get(pos).getType().equals(Cell.Style.CellType.BORDER)){
                    ArrayList<Position> voisins = pos.voisins(etage);
                    if(voisins.size()>5){
                        boolean novoidVoisins = true;
                        for(Position p : voisins){
                            if(etage.get(p).getType().equals(Cell.Style.CellType.VOID)){
                                novoidVoisins = false;
                                break;
                            }
                        }
                        if(novoidVoisins){
                            etage.get(pos).updateCell(true, style_fusion);
                        }
                    }
                }
            }
        }
    }

    /**
     * Definit les cellules spéciales.
     * @param etage etage courant
     * @param etageDepart Defini si s'est l'etage de depart
     * @author Quentin
     */
    public void setSpecialCell(Etage etage, boolean etageDepart){
        if(etageDepart){
            Procedure.setRandomDOWN(etage);
        }
        else{
            Procedure.setRandomUPnDOWN(etage);
        }
        etage.setTrapCell();
    }

    /**
     * Defini les monstres qui seront présents dans l'étage.
     * @param etage etage courant
     * @author Quentin
     */
    public void setMonsters(Etage etage){
        for(Room r : etage.getRooms()){
            r.setMonsters(etage);
        }

    }

    /**
     * Defini les items au sol dans l'étage.
     * @param etage etage courant
     * @author JP
     */
    public void setItems(Etage etage) {
        for (Room r : etage.getRooms()) {
            r.setItems(etage);
        }
        etage.addItem(Objects.requireNonNull(WeaponFactory.getNewWeapon(etage, WeaponFactory.WeaponType.WAND)));
    }

}
