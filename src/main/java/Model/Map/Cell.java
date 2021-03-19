package Model.Map;

import Model.Utils.Affichage;
import Model.Entitys.Entity;

public class Cell{
    public enum CellType {
        BORDER( Affichage.BOLD+Affichage.RED+"*"+Affichage.RESET),
        NORMAL(Affichage.GREY+"."),
        PATH(Affichage.BOLD+Affichage.BLUE+"*"+Affichage.RESET),
        VOID(Affichage.BLACK+"."),
        CHEST("\uD83D\uDCB0",Affichage.YELLOW+Affichage.BOLD+"$"),
        UP("\uD83D\uDC4D",Affichage.BLUE+Affichage.BOLD+"^"),
        DOWN("\uD83D\uDC4E",Affichage.BLUE+Affichage.BOLD+"v"),
        TRAP_ROOM(Affichage.BLUE+"X"),
        SPECIAL(Affichage.YELLOW+"X");

        private String string;

        CellType(String s) {
            string=s;
        }

        CellType(String s1, String s2) {
            if(System.getProperty("os.name").equals("Linux")){
                string=s1;
            }
            else{
                string=s2;
            }
        }

        public String getString(){
            return string;
        }

        public void setString(String s){
            this.string=s;
        }

    }
    private boolean isAccesible;
    private CellType type;
    private Model.Entitys.Entity Entity;

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
                || type.equals(CellType.TRAP_ROOM)
                || type.equals(CellType.SPECIAL);
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

    public Cell copyOf(){
        Cell cell = new Cell(isAccesible, type);
        cell.Entity=Entity;
        return cell;
    }

    @Override
    public String toString() {
        if(Entity == null) {
            return type.getString();
        }
        else {
            return Entity.toString();
        }
    }
}