package Model;

import java.util.ArrayList;

public class Room extends Map {
    public static final int MinSize=5;
    public static final int MaxSize=20;
    public static final int nbrMaxMobPerRoom=5;
    private Position AbsolutePos=null;
    private ArrayList<Position> Doors = new ArrayList<>();

    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        setBorders();
    }

    /**
     * Bordure de la Room;
     */
    private void setBorders(){
        fillMap(Cell.CellType.NORMAL);
        for (int i = 0; i < SIZEX; i++) {
            this.set(i,0,new Cell(false, Cell.CellType.BORDER));
            this.set(i,SIZEY-1,new Cell(false, Cell.CellType.BORDER));
            if (i==0 || i==(SIZEX-1)){
                for (int j = 0; j < SIZEY; j++) {
                    if(j==0 || j==(SIZEY-1)){
                        this.set(i,j,new Cell(false, Cell.CellType.ANGLE));
                        this.set(i,j,new Cell(false, Cell.CellType.ANGLE));
                    }
                    else{
                        this.set(i,j,new Cell(false, Cell.CellType.BORDER));
                        this.set(i,j,new Cell(false, Cell.CellType.BORDER));
                    }
                }
            }
        }
    }

    public void addDoors(Position pos){
        Doors.add(pos);
    }

    public void addDoors(ArrayList<Position> pos){
        Doors=pos;
    }

    public ArrayList<Position> getDoors(){
        return Doors;
    }

    public void setAbsolutePos(Position pos){
        AbsolutePos=Position.copyOf(pos);
    }

    public Position getAbsolutePos() {
        return AbsolutePos;
    }
}
