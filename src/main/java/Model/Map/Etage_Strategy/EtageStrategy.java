package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

public abstract class EtageStrategy {

    public static EtageStrategy getRandomStrategy(){
        ArrayList<EtageStrategy> strategies = new ArrayList<>();
        strategies.add(new NormalEtageStrategy());
        //strategies.add(new TrapEtageStrategy());
        strategies.add(new CircleEtageStrategy());
        return strategies.get(Procedure.getRandomInt(strategies.size(), 0));
    }

    public abstract void composeEtage(Etage etage);

    protected void EtageFusion(Etage etage){
        //Trace du chemin
        for (int i = 0; i < etage.getRooms().size()-1; i++) {
            Position pos1= Procedure.getRandomPosition(etage.getRooms().get(i));
            Position pos2= Procedure.getRandomPosition(etage.getRooms().get(i+1));
            Tools.ligne(etage, pos1, pos2, Cell.CellType.NORMAL,Procedure.getRandomInt(6,0));
        }

        //Ajout des murs aux chemins
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos=new Position(x, y);
                if (etage.get(pos).getType().equals(Cell.CellType.VOID)) {
                    ArrayList<Position> voisins = pos.voisins(etage);
                    for (Position p : voisins) {
                        if (!etage.get(p).getType().equals(Cell.CellType.BORDER) && !etage.get(p).getType().equals(Cell.CellType.VOID)){
                            etage.get(x,y).updateCell(false, Cell.CellType.BORDER);
                        }
                    }
                }
            }
        }
        //Suppression des murs inutiles
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                Position pos=new Position(x, y);
                if(etage.get(pos).getType().equals(Cell.CellType.BORDER)){
                    ArrayList<Position> voisins = pos.voisins(etage);
                    if(voisins.size()>5){
                        boolean novoidVoisins = true;
                        for(Position p : voisins){
                            if(etage.get(p).getType().equals(Cell.CellType.VOID)){
                                novoidVoisins = false;
                                break;
                            }
                        }
                        if(novoidVoisins){
                            etage.get(pos).updateCell(true, Cell.CellType.NORMAL);
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
        Position accesibleRandomPosition = Procedure.getAccesibleRandomPosition(false, etage);
        etage.get(accesibleRandomPosition).updateCell(true, Cell.CellType.TRAP_ROOM);
    }

    public void setMobs(Etage etage){
        Procedure.setRandomMob(etage);
    }

}
