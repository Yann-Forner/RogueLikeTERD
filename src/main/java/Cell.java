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

    @Override
    public String toString() {
        switch (type){
            case BORDER -> {
                return "\u001B[31m*";
            }
            case NORMAL -> {
                return "\u001B[31m.";
            }
            case DOOR -> {
                return "\u001B[31m=";
            }
            default -> {
                return "\u001B[31m ";
            }
        }
    }
}
