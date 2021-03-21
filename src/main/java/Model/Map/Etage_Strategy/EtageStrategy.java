package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

public abstract class EtageStrategy {

    public abstract void composeEtage(Etage etage);

    protected void RoomFusion(Etage etage){
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
                        if (etage.get(p).getType().equals(Cell.CellType.NORMAL)) {
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
                ArrayList<Position> voisins = pos.voisins(etage);
                if(voisins.size()>6){
                    boolean isUseless=true;
                    for(Position p : voisins){
                        if(etage.get(p).getType().equals(Cell.CellType.VOID)){
                            isUseless=false;
                            break;
                        }
                    }
                    if(isUseless){
                        etage.get(pos).updateCell(true, Cell.CellType.NORMAL);
                    }
                }
            }
        }
    }

    public abstract int getNbrMaxRoom();

}
