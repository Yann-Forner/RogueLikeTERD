import java.util.ArrayList;
import java.util.Collections;

public class Room extends Map {
    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        super.fillMap();
        //setBorders();
    }

    private void setBorders(){
        for (int i = 0; i < this.SIZEX ; i++) {
            this.set(i,0,new Cell(false, Cell.CellType.BORDER));
            this.set(i, this.SIZEY-1,new Cell(false, Cell.CellType.BORDER));
        }
        for (int i = 0; i < this.SIZEY ; i++) {
            this.set(0,i,new Cell(false, Cell.CellType.BORDER));
            this.set( this.SIZEX-1,i,new Cell(false, Cell.CellType.BORDER));
        }
    }

}
