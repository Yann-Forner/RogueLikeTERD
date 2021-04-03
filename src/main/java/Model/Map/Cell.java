package Model.Map;

import Model.Entitys.Entity;
import Model.Utils.Affichage;

public class Cell{

    public static class Style{
        public enum CellType {
            BORDER(Affichage.BOLD+Affichage.RED,"*"),
            NORMAL(Affichage.GREEN,"."),
            PATH(Affichage.BOLD+Affichage.BLUE,"*"),
            VOID(Affichage.BLACK,"."),
            CHEST(Affichage.YELLOW+Affichage.BOLD,"\uD83D\uDCB0","$"),
            UP(Affichage.BLUE+Affichage.BOLD,"\uD83D\uDC4D","^"),
            DOWN(Affichage.BLUE+Affichage.BOLD,"\uD83D\uDC4E","v"),
            TRAP_ROOM(Affichage.BLUE,"X"),
            SPECIAL(Affichage.YELLOW,"X");

            private final String base_color;
            private final String forme;

            CellType(String c, String s) {
                base_color=c;
                forme=s;
            }

            CellType(String c, String s1, String s2) {
                this(c,System.getProperty("os.name").equals("Linux") ? s1 : s2);
            }
        }
        private final CellType type;
        private String custom_color;

        public Style(CellType type){
            this.type=type;
        }

        public Style(CellType type,String color){
            this(type);
            this.custom_color=color;
        }

        @Override
        public String toString() {
            return type.forme.length() > 1 ? type.forme : custom_color == null ? type.base_color + type.forme : custom_color + type.forme;
        }
    }

    private Style style;
    private boolean isAccesible;
    private Model.Entitys.Entity Entity;

    public Cell(boolean isAccesible, Style style) {
        this.isAccesible = isAccesible;
        this.style = style;
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
        return getType().equals(Style.CellType.UP)
                || getType().equals(Style.CellType.DOWN)
                || getType().equals(Style.CellType.CHEST)
                || getType().equals(Style.CellType.TRAP_ROOM)
                || getType().equals(Style.CellType.SPECIAL);
    }

    public Style getStyle() {
        return style;
    }

    public Style.CellType getType() {
        return style.type;
    }

    public void updateCell(boolean isAccesible, Style style){
        this.isAccesible = isAccesible;
        this.style = style;
    }

    public Entity getEntity() {
        return Entity;
    }

    public void setEntity(Entity entity) {
        Entity = entity;
    }

    public Cell copyOf(){
        Cell cell = new Cell(isAccesible, style);
        cell.Entity=Entity;
        return cell;
    }

    @Override
    public String toString() {
        if(Entity == null) {
            return style.toString();
        }
        else {
            return Entity.toString();
        }
    }
}