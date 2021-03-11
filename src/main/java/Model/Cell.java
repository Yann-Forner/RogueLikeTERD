package Model;

public class Cell{
    public enum CellType {
        BORDER, NORMAL, PATH, VOID, CHEST, UP, DOWN, ;
    }
    private boolean isAccesible;
    private CellType type;
    private Entity Entity;

    public Cell(boolean isAccesible, CellType type) {
        this.isAccesible = isAccesible;
        this.type = type;
        Entity = null;
    }

    public boolean isAccesible() {
        return isAccesible;
    }

    public CellType getType() {
        return type;
    }

    public void updateCell(boolean isAccesible, CellType type){
        this.isAccesible=isAccesible;
        this.type=type;
    }

    public Entity getEntity() {
        return Entity;
    }

    public void setEntity(Entity entity) {
        Entity = entity;
    }

    @Override
    public String toString() {
        if(Entity == null) {
            switch (type){
                case NORMAL -> {
                    return Affichage.GREY+".";
                }
                case VOID -> {
                    return Affichage.BLACK+".";
                }
                case PATH -> {
                    return Affichage.BOLD+Affichage.BLUE+"*"+Affichage.RESET;
                }
                case BORDER -> {
                    return Affichage.BOLD+Affichage.RED+"*"+Affichage.RESET;
                }
                case CHEST -> {
                    return "\u001b[38;5;220m"+'$';
                }
                case UP -> {
                    return Affichage.BLUE+'^';
                }
                case DOWN -> {
                    return Affichage.BLUE+'v';
                }
                default -> {
                    return Affichage.RED+'.';
                }
            }
        }
        else {
            return Entity.toString();
        }
    }
}