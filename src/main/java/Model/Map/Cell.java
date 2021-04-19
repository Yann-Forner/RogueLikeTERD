package Model.Map;

import Model.Utils.Affichage;

public class Cell{

    public static class Style{
        public enum CellType {
            BORDER(Affichage.BOLD+Affichage.RED,"*"),
            NORMAL(Affichage.GREEN,"."),
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
        private String custom_forme_linux;
        private String custom_forme_windows;

        public Style(CellType type){
            this.type=type;
        }

        public Style(CellType type,String color){
            this(type);
            this.custom_color=color;
        }

        public Style(CellType type,String color,String forme){
            this(type,color);
            this.custom_forme_linux=forme;
        }

        public Style(CellType type,String color,String forme_linux, String forme_windows){
            this(type,color,forme_linux);
            this.custom_forme_windows=forme_windows;
        }

        @Override
        public String toString() {
            String forme= System.getProperty("os.name").equals("Linux") ? custom_forme_linux==null ? type.forme : custom_forme_linux : custom_forme_windows==null ? type.forme : custom_forme_windows;
            return forme.length() > 1 ? forme : custom_color == null ? type.base_color + forme : custom_color + forme;
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

    public Model.Entitys.Entity getEntity() {
        return Entity;
    }

    public void setEntity(Model.Entitys.Entity entity) {
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