package Model;

public class Cell{
    public enum CellType {
        BORDER, NORMAL, DOOR, PATH, VOID, ANGLE, ;
    }
    private final boolean isAccesible;
    private final CellType type;
    private Entity Entity;

    public Cell(boolean isAccesible, CellType type) {
        this.isAccesible = isAccesible;
        this.type = type;
        this.Entity = null;
    }

    public boolean isAccesible() {
        return isAccesible;
    }

    public CellType getType() {
        return type;
    }

    public Model.Entity getEntity() {
        return Entity;
    }

    public void setEntity(Model.Entity entity) {
        Entity = entity;
    }

    @Override
    public String toString() {
        if(this.Entity == null) {
            switch (type){
                case NORMAL -> {
                    return "\u001B[37m.";
                }
                case VOID -> {
                    return "\u001B[30m.";
                }
                case PATH -> {
                    return "\u001B[34m*";
                }
                case ANGLE, BORDER -> {
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
        else {
            return this.Entity.toString();
        }
    }
}