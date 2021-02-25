package Model;

public class Cell{
    public enum CellType {
        BORDER, NORMAL, DOOR, VOID;
    }
    private final boolean isAccesible;
    private final CellType type;

    public Cell(boolean isAccesible, CellType type) {
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
            case VOID, NORMAL -> {
                return "\u001B[37m.";
            }
            case BORDER -> {
                return "\u001B[31m*";
            }
            case DOOR -> {
                return "\u001B[35m=";
            }
            default -> {
                return "\u001B[31m ";
            }
        }
    }

}