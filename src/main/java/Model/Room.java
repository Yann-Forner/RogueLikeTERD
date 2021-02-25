package Model;

import java.util.ArrayList;

public class Room extends Map {
    public static final int MinSize=5;
    public static final int MaxSize=20;
    private ArrayList<Position> Doors = new ArrayList();

    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        setBorders();
    }

    private void setBorders(){
        for (int i = 0; i < SIZEX; i++) {
            this.set(i,0,new Cell(false, Cell.CellType.BORDER));
            this.set(i,SIZEY-1,new Cell(false, Cell.CellType.BORDER));
            if (i==0 || i==(SIZEX-1)){
                for (int j = 1; j < SIZEY; j++) {
                    this.set(i,j,new Cell(false, Cell.CellType.BORDER));
                    this.set(i,j,new Cell(false, Cell.CellType.BORDER));
                }
            }
        }
    }

    public void addDoors(Position pos){
        Doors.add(pos);
    }

    public ArrayList<Position> getDoors(){
        return Doors;
    }

    public void setDoors(ArrayList<Position> d){
        Doors = d;
    }

}
