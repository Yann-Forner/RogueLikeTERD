package Model;

public class Cell{
    public enum CellType {
        BORDER, NORMAL, PATH, VOID, CHEST, ;
    }
    private boolean isAccesible;
    private CellType type;
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
        if(this.Entity == null) {
            switch (type){
                case NORMAL -> {
                    return Affichage.GREY+'.';
                }
                case VOID -> {
                    return Affichage.BLACK+'.';
                }
                case PATH -> {
                    return Affichage.BLUE+'*';
                }
                case BORDER -> {
                    return Affichage.RESET+Affichage.GREY_BACKGROUND+Affichage.CROSSED+" |";
                }
                case CHEST -> {
                    return Affichage.YELLOW+'$';
                }
                default -> {
                    return Affichage.RED+'.';
                }
            }
        }
        else {
            return this.Entity.toString();
        }
    }
}