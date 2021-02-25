import java.util.ArrayList;
import java.util.Collections;

public class Map {

protected final int SIZEX = 200;
protected final int SIZEY = 100;
protected ArrayList<ArrayList<Cell>> myMap;

    public Map() {
        fillMap();
    }

    public void fillMap(){
        myMap = new ArrayList<>(Collections.nCopies(this.SIZEY,new ArrayList<>(Collections.nCopies(this.SIZEX,new Cell(true, Cell.CellType.NORMAL)))));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Cell> a : this.myMap
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
        return this.myMap.get(y).get(x);
    }
    public void set(int x , int y, Cell c){
        this.myMap.get(y).set(x,c);
    }

    public int getSIZEX() {
        return SIZEX;
    }

    public int getSIZEY() {
        return SIZEY;
    }
}
