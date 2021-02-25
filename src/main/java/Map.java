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
        Cells = new ArrayList<>(Collections.nCopies(this.SIZEY,new ArrayList<>(Collections.nCopies(this.SIZEX,new Cell(true, Cell.CellType.NORMAL)))));
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

    public void addRoom(Room r , Position p){
        for (int y = 0; y < r.getSIZEY() ; y++) {
            for (int x = 0; x < r.getSIZEX() ; x++) {
                this.set(p.getX()+x, p.getY()+y ,r.get(x,y) );
            }
        }
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
