package Model;

public class Room extends Map {
    public static final int MinSize=5;
    public static final int MaxSize=20;
    public static final int nbrMaxMobPerRoom=5;
    private Position AbsolutePos=null;

    public Room(int SIZEX, int SIZEY) {
        super(SIZEX, SIZEY);
        setBorders();
    }

    /**
     * Bordure de la Room;
     */
    private void setBorders(){
        fillMap(new Cell(true, Cell.CellType.NORMAL));
        for (int i = 0; i < SIZEX; i++) {
            get(i,0).updateCell(false, Cell.CellType.BORDER);
            get(i,SIZEY-1).updateCell(false, Cell.CellType.BORDER);
            if (i==0 || i==(SIZEX-1)){
                for (int j = 0; j < SIZEY; j++) {
                    get(i,j).updateCell(false, Cell.CellType.BORDER);
                }
            }
        }
    }

    public void setAbsolutePos(Position pos){
        AbsolutePos=pos.copyOf();
    }

    public Position getAbsolutePos() {
        return AbsolutePos.copyOf();
    }
}
