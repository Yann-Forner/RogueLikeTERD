public class Cell{
    public enum CellType {
        BORDER, NORMAL, DOOR;
    }
    private final boolean isAccesible;
    private final Position pos;
    private final CellType type;

    Cell(int x, int y, boolean isAccesible, CellType type) {
        this.pos = new Position(x,y);
        this.isAccesible = isAccesible;
        this.type = type;
    }

    public boolean isAccesible() {
        return isAccesible;
    }

    public Position getPos() {
        return pos;
    }

    public CellType getType() {
        return type;
    }
}
