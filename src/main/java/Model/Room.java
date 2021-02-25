package Model;

import Model.Cell;
import Model.Map;

public class Room extends Map {
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
}
