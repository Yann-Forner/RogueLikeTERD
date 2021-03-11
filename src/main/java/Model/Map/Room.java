package Model.Map;

import Model.Cell;
import Model.Map.Map;
import Model.Position;

public class Room extends Map {
    public static final int MinSize=5;
    public static final int MaxSize=20;
    public static final int nbrMaxMobPerRoom=5;
    private Position AbsolutePos=null;

    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        fillMap(new Cell(true, Cell.CellType.NORMAL));
    }

    public void setAbsolutePos(Position pos){
        AbsolutePos=pos.copyOf();
    }

    public Position getAbsolutePos() {
        return AbsolutePos.copyOf();
    }
}
