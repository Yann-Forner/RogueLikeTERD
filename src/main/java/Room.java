import java.util.ArrayList;
import java.util.Collections;

public class Room extends Map {
    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        fillCases();
    }

    @Override
    public void fillCases(){
        this.myMap = new ArrayList<>(this.SIZEY);
        for (int i = 0; i < this.SIZEY ; i++) {
            this.myMap.add(new ArrayList<>(Collections.nCopies(this.SIZEX,Cell.SPACE)));
        }
        setBorders();
    }

    private void setBorders(){
        for (int i = 0; i < this.SIZEX ; i++) {
            this.set(i,0,Cell.BORDER);
            this.set(i, this.SIZEY-1,Cell.BORDER);
        }
        for (int i = 0; i < this.SIZEY ; i++) {
            this.set(0,i,Cell.BORDER);
            this.set( this.SIZEX-1,i,Cell.BORDER);
        }
    }

}
