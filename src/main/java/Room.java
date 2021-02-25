import java.util.ArrayList;
import java.util.Collections;

public class Room extends Map {
    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        setBorders();
    }

    private void setBorders(){
        for (int i = 0; i < SIZEX; i++) {
            this.set(0,i,new Cell(false, Cell.CellType.BORDER));
            this.set(SIZEX-1,i,new Cell(false, Cell.CellType.BORDER));
            if (i==0 || i==(SIZEX-1)){
                for (int j = 1; j < SIZEY-1; j++) {
                    this.set(j,i,new Cell(false, Cell.CellType.BORDER));
                    this.set(j,i,new Cell(false, Cell.CellType.BORDER));
                }
            }
        }
    }

}
