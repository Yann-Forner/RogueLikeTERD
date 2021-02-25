import java.util.ArrayList;
import java.util.Collections;

public class Map {

protected int SIZEX;
protected int SIZEY;
protected ArrayList<ArrayList<Cell>> Cells;

    public Map(int SIZEX, int SIZEY) {
        this.SIZEX=SIZEX;
        this.SIZEY=SIZEY;
        fillMap();
    }

    public void fillMap(){
        Cells = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList line = new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                line.add(j, new Cell(true, Cell.CellType.NORMAL));
            }
            Cells.add(i, line);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Cell> a : Cells
             ) {
            for ( Cell c : a
                 ) {
                sb.append(c).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Cell get(int x , int y){
        return Cells.get(y).get(x);
    }
    public void set(int x , int y, Cell c){
        Cells.get(y).set(x,c);
    }

    public int getSIZEX() {
        return SIZEX;
    }

    public int getSIZEY() {
        return SIZEY;
    }
}
