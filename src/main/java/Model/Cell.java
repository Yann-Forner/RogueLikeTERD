package Model;

public class Cell{
    public enum CellType {
        BORDER, NORMAL, PATH, VOID, CHEST, UP, DOWN, TRAP_ROOM, ;
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

    /**
     * Les cellules reservé ne permetent pas aux entitées de se generer dessus.
     * @return boolean
     */
    public boolean isReserved(){
        return type.equals(CellType.UP)
                || type.equals(CellType.DOWN)
                || type.equals(CellType.CHEST)
                || type.equals(CellType.TRAP_ROOM);
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
                    return "\uD83D\uDCB0";
                }
                case UP -> {
                    return "\uD83D\uDC4D";
                }
                case DOWN -> {
                    return 	"\uD83D\uDC4E";
                }
                case TRAP_ROOM -> {
                    return 	Affichage.BLUE+"X";
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