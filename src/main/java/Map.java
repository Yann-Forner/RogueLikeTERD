import java.util.ArrayList;
import java.util.Collections;

public class Map {

protected final int SIZEX;
protected final int SIZEY;
protected ArrayList<ArrayList<Cell>> myMap;

    public Map(int SIZEX, int SIZEY) {
        this.SIZEX = SIZEX;
        this.SIZEY = SIZEY;
        fillCases();
    }

    public void fillCases(){

        myMap = new ArrayList<>(this.SIZEY);
        for (int i = 0; i < this.SIZEY ; i++) {
            myMap.add(new ArrayList<>(Collections.nCopies(this.SIZEX,Cell.VOID)));
        }
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
