public class Cell{
    public enum CellType {
        BORDER, NORMAL, DOOR;
    }
    private final boolean isAccesible;
    private final CellType type;

    Cell(boolean isAccesible, CellType type) {
        this.isAccesible = isAccesible;
        this.type = type;
    }

    public boolean isAccesible() {
        return isAccesible;
    }

    public CellType getType() {
        return type;
    }
}
