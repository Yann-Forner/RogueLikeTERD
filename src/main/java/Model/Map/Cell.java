package Model.Map;

import Model.Utils.Affichage;

import java.io.Serializable;

/**
 * Classe des cellules.
 * @author Yann, Quentin
 */
public class Cell implements Serializable {

    /**
     * Style de la cellule.
     * @author Quentin
     */
    public static class Style implements Serializable{
        public enum CellType {
            BORDER(Affichage.BOLD+Affichage.RED,"*"),
            NORMAL(Affichage.GREEN,"."),
            VOID(Affichage.BLACK,"."),
            CHEST(Affichage.YELLOW+Affichage.BOLD,"\uD83D\uDCB0","$"),
            UP(Affichage.BLUE+Affichage.BOLD,"\uD83D\uDC4D","^"),
            DOWN(Affichage.BLUE+Affichage.BOLD,"\uD83D\uDC4E","v"),
            TRAP_ROOM(Affichage.BLUE,"X"),
            PROJECTILE(Affichage.RED+Affichage.BOLD,"\uD83D\uDD25","+");

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

        /**
         * Crée une cellule selon son type.
         * @param type Type
         * @author Yann
         */
        public Style(CellType type){
            this.type=type;
        }

        /**
         * Crée une cellule selon son type avec une couleure modifié.
         * @param type Type
         * @param color Couleure
         * @author Quentin
         */
        public Style(CellType type,String color){
            this(type);
            this.custom_color=color;
        }

        /**
         * Crée une cellule selon son type avec une couleure et une forme modifiées.
         * @param type Type
         * @param color Couleure
         * @param forme Forme
         * @author Quentin
         */
        public Style(CellType type,String color,String forme){
            this(type,color);
            this.custom_forme_linux=forme;
        }

        /**
         * Crée une cellule selon son type avec une couleure et une forme modifiées selon l'OS.
         * @param type Type
         * @param color Couleure
         * @param forme_linux Forme Linux
         * @param forme_windows Forme Windows
         * @author Quentin
         */
        public Style(CellType type,String color,String forme_linux, String forme_windows){
            this(type,color,forme_linux);
            this.custom_forme_windows=forme_windows;
        }

        @Override
        public String toString() {
            //TODO decalage inelij
            String forme, color;
            if(System.getProperty("os.name").equals("Linux")){
                if(custom_forme_linux==null){
                    if(type.forme.length()==2){
                        return type.forme;
                    }
                    else{
                        forme = type.forme;
                    }
                }
                else{
                    return custom_forme_linux;
                }
            }
            else{
                forme = custom_forme_windows==null ?  type.forme : custom_forme_windows;
            }
            color = custom_color == null ? type.base_color : custom_color;
            return color + forme;
        }
    }

    private Style style;
    private boolean isAccesible;
    private Model.Entitys.Entity Entity;

    /**
     * Crée une cellule accesible ou non avec son style.
     * @param isAccesible boolean
     * @param style Style
     * @author Yann
     */
    public Cell(boolean isAccesible, Style style) {
        this.isAccesible = isAccesible;
        this.style = style;
        Entity = null;
    }

    /**
     * Renvoit vrai si les entités peuvent se deplacer dessus.
     * @return boolean
     * @author Yann
     */
    public boolean isAccesible() {
        return isAccesible;
    }

    /**
     * Les cellules reservé ne permetent pas aux entitées de se generer dessus.
     * @return boolean
     * @author Quentin
     */
    public boolean isReserved(){
        return getType().equals(Style.CellType.UP)
                || getType().equals(Style.CellType.DOWN)
                || getType().equals(Style.CellType.CHEST)
                || getType().equals(Style.CellType.TRAP_ROOM);
    }

    /**
     * Renvoit le Style de la cellule.
     * @return style
     * @author Quentin
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Renvoit le type de la cellule
     * @return type
     * @author Quentin
     */
    public Style.CellType getType() {
        return style.type;
    }

    /**
     * Change les propiétés de la cellule.
     * @param isAccesible boolean
     * @param style Style
     * @author Quentin
     */
    public void updateCell(boolean isAccesible, Style style){
        this.isAccesible = isAccesible;
        this.style = style;
    }

    /**
     * Renvoit l'entité presente sur la cellule.
     * @return Entity
     * @author Quentin
     */
    public Model.Entitys.Entity getEntity() {
        return Entity;
    }

    /**
     * Defini l'entité presente sur la cellule.
     * @author Quentin
     */
    public void setEntity(Model.Entitys.Entity entity) {
        Entity = entity;
    }

    /**
     * Copie la cellule.
     * @return Cell
     * @author Quentin
     */
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