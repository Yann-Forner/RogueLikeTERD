package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;

public class TrapEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage,this, RoomFactory.roomType.TRAP);
        EtageFusion(etage);
        Procedure.setRandomUP(etage);
        Procedure.setRandomMob(etage);
    }

    @Override
    public void EtageFusion(Etage etage) {
        Cell.CellType typeNormal = Cell.CellType.NORMAL;
        typeNormal.setString(Affichage.GREY+'.');
        ArrayList<Position> doors = new ArrayList<>();
        for(Room r : etage.getRooms()) {
            for (int i = 0; i < r.getHeigth(); i++) {
                if(i==0 || i==r.getHeigth()-1){
                    for (int j = 0; j < r.getWidth(); j++) {
                        if(r.get(j,i).getType().equals(Cell.CellType.NORMAL)){
                            doors.add(new Position(j,i).somme(r.getAbsolutePos()));
                        }
                    }
                }
                if(r.get(0,i).getType().equals(Cell.CellType.NORMAL)){
                    doors.add(new Position(0,i).somme(r.getAbsolutePos()));
                }
                if(r.get(r.getWidth()-1,i).getType().equals(Cell.CellType.NORMAL)){
                    doors.add(new Position(r.getWidth()-1,i).somme(r.getAbsolutePos()));
                }
            }
        }
        ArrayList<Position> visited = new ArrayList<>();
        visited.add(doors.get(0));
        for (int i = 1; i < doors.size(); i++) {
            ArrayList<Position> astar = Tools.Astar(etage, doors.get(i), visited.get(visited.size() - 1), -1);
            for (Position p : astar){
                etage.get(p).updateCell(true, typeNormal);
            }
        }
        for(Room r : etage.getRooms()) {
            for (int i = 1; i < r.getHeigth()-1; i++) {
                for (int j = 1; j < r.getWidth()-1; j++) {
                    Position pos = new Position(j,i).somme(r.getAbsolutePos());
                    etage.get(pos).updateCell(true, typeNormal);
                }
            }
        }
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
    }

    @Override
    public int getNbrMaxRoom() {
        return 20;
    }
}