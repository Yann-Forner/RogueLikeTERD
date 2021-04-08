package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

public abstract class EtageStrategy {

    public static EtageStrategy getRandomStrategy(){
        ArrayList<EtageStrategy> strategies = new ArrayList<>();
        strategies.add(new NormalEtageStrategy());// 3/10
        strategies.add(new NormalEtageStrategy());
        strategies.add(new NormalEtageStrategy());
        strategies.add(new DonjonStrategy());// 5/10
        strategies.add(new DonjonStrategy());
        strategies.add(new DonjonStrategy());
        strategies.add(new DonjonStrategy());
        strategies.add(new DonjonStrategy());
        strategies.add(new CircleEtageStrategy());// 2/10
        strategies.add(new CircleEtageStrategy());
        return strategies.get(Procedure.getRandomInt(strategies.size(), 0));
    }

    public abstract void composeEtage(Etage etage);

    protected void EtageFusion(Etage etage, Cell.Style style_fusion){
        //Trace du chemin
        for (int i = 0; i < etage.getRooms().size()-1; i++) {
            Room r1 = etage.getRooms().get(i);
            Room r2 = etage.getRooms().get(i+1);
            //TODO faire sleon la start de la room
            Tools.ligne(etage, new Position(r1.getWidth()/2,r1.getHeigth()/2).somme(r1.getAbsolutePos()), new Position(r2.getWidth()/2,r2.getHeigth()/2).somme(r2.getAbsolutePos()), style_fusion, Procedure.getRandomInt(6,0));
        }
        //Ajout des murs aux chemins
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos=new Position(x, y);
                if (etage.get(pos).getType().equals(Cell.Style.CellType.VOID)) {
                    ArrayList<Position> voisins = pos.voisins(etage);
                    for (Position p : voisins) {
                        if (!etage.get(p).getType().equals(Cell.Style.CellType.BORDER) && !etage.get(p).getType().equals(Cell.Style.CellType.VOID)){
                            etage.get(x,y).updateCell(false, new Cell.Style(Cell.Style.CellType.BORDER));
                        }
                    }
                }
            }
        }
        //Suppression des murs inutiles
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos=new Position(x, y);
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

    public abstract int getNbrMaxRoom();

    public void setSpecialCell(Etage etage){
        Procedure.setRandomChest(etage,3);
        Procedure.setRandomUPnDOWN(etage);
        etage.setTrapCell();
    }

    public void setMobs(Etage etage){
        Procedure.setRandomMob(etage);
    }

}
