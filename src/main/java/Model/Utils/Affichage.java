package Model.Utils;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Classe de l'affichage du jeu
 * @author Quentin,Yann
 */
public class Affichage {
    public static final String CLEAR = "\033\143";
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String FAINT = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[6m";
    public static final String REVERSE = "\u001B[7m";
    public static final String CROSSED = "\u001B[9m";
    public static final String DOUBLE_UNDERLINE = "\u001B[21m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String GREY_BACKGROUND = "\u001B[47m";
    public static final String FRAMED = "\u001B[51m";
    public static final String BRIGTH_BLACK = "\u001B[90m";
    public static final String BRIGTH_RED = "\u001B[91m";
    public static final String BRIGTH_GREEN = "\u001B[92m";
    public static final String BRIGTH_YELLOW = "\u001B[93m";
    public static final String BRIGTH_BLUE = "\u001B[94m";
    public static final String BRIGTH_PURPLE = "\u001B[95m";
    public static final String BRIGTH_CYAN = "\u001B[96m";
    public static final String BRIGTH_GREY = "\u001B[97m";
    public static final String BRIGTH_BLACK_BACKGROUND = "\u001B[100m";
    public static final String BRIGTH_RED_BACKGROUND = "\u001B[101m";
    public static final String BRIGTH_GREEN_BACKGROUND = "\u001B[102m";
    public static final String BRIGTH_YELLOW_BACKGROUND = "\u001B[103m";
    public static final String BRIGTH_BLUE_BACKGROUND = "\u001B[104m";
    public static final String BRIGTH_PURPLE_BACKGROUND = "\u001B[105m";
    public static final String BRIGTH_CYAN_BACKGROUND = "\u001B[106m";
    public static final String BRIGTH_GREY_BACKGROUND = "\u001B[107m";

    private static long lastRefresh = System.currentTimeMillis();

    public enum Shadow{
        CIRCLE,RAY,NONE
    }
    private static Shadow ombre = Shadow.NONE;

    /**
     * Affiche la map
     * @author Quentin
     */
    public synchronized static void getMap(boolean playerRefresh){
        if(System.currentTimeMillis()-lastRefresh >= TourManager.getRefreshRate() || playerRefresh) {
            Start.setConsoleMode(false);
            System.out.print(System.console() == null ?
                    Affichage.BRIGTH_RED
                            + "---------------------------------------------------------------------------------------------------------"
                            + "---------------------------------------------------------------------------------------------------------"
                            + "\n"
                            + "                                                                                                     "
                            + Affichage.YELLOW
                            + "TOUR: "
                            + Start.getTourManager().getTour()
                            + Affichage.BRIGTH_RED
                            + "                                                                                                     "
                            + "\n"
                            + "---------------------------------------------------------------------------------------------------------"
                            + "---------------------------------------------------------------------------------------------------------"
                            + "\n"
                    : CLEAR);
            System.out.println(Start.getMap());
            String pv = Affichage.getBarre(GREEN, "PV", GREY, Objects.requireNonNull(Start.getPlayer()).getPv(), Start.getPlayer().getMAX_PV(), GREEN_BACKGROUND, RED_BACKGROUND, BLACK_BACKGROUND,120);
            System.out.print(pv);
            System.out.print(Affichage.getTouches(0, getTrueLength(pv)));
            String endurence = Affichage.getBarre(BLUE, "Endurence", GREY, Start.getPlayer().getEndurence(), 100, BLUE_BACKGROUND, RED_BACKGROUND, BLACK_BACKGROUND, 100);
            System.out.print(endurence);
            System.out.print(Affichage.getTouches(1, getTrueLength(endurence)));
            Affichage.getMessages();
            Start.setConsoleMode(true);
            lastRefresh = System.currentTimeMillis();
        }
    }

    /**
     * Renvoi l'étage en String
     * @param etage etage courrant
     * @return String de l'étage
     * @author Quentin,Yann
     */
    public static String etage(Etage etage){
        Menu menu = new Menu(etage);
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append(RESET);
        for (int x = 0; x < etage.getWidth(); x++) {
            sb.append(x);
            if (x < 10) {
                sb.append("  ");
            } else {
                sb.append(" ");
            }
        }

        //Champs de vision
        HashSet<Position> visibles = new HashSet<>();
        //TODO probleme avec le menu
        if(Start.getPlayer()!=null && ombre.equals(Shadow.RAY)){
            for(Position p : Tools.getBorder(etage)){
                visibles.addAll(Tools.getVisibles(etage, etage instanceof Room ? new Position(etage.getWidth()/2,etage.getHeigth()/2) : Start.getPlayer().getPosition(), p));
            }
        }

        //Titre Menu
        sb.append("    ");
        sb.append("                                      ");
        sb.append(BRIGTH_YELLOW).append(BOLD);
        sb.append("[MENU]");
        sb.append(RESET);
        sb.append("                                      ");
        sb.append("\n");

        //Numero cases Map
        for (int y = 0; y < etage.getHeigth(); y++) {
            sb.append(RESET);
            if (y < 10) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(y).append(" ");
            }
            //MAP
            for (int x = 0; x < etage.getWidth(); x++) {
                sb.append(RESET);
                Position pos = new Position(x,y);
                Cell cell;
                switch (ombre){
                    case CIRCLE -> cell = Start.getPlayer().getPosition().Distance(pos) <= Start.getPlayer().getVision_radius() ? etage.get(pos) : new Cell(etage.get(x, y).isAccesible(), new Cell.Style(Cell.Style.CellType.VOID));
                    case RAY -> cell = visibles.contains(pos) ? etage.get(pos) : new Cell(etage.get(x, y).isAccesible(), new Cell.Style(Cell.Style.CellType.VOID));
                    case NONE -> cell = etage.get(pos);
                    default -> throw new IllegalStateException("Unexpected value: " + ombre);
                }
                sb.append(" ").append(cell);
                int length = cell.toString().length();
                if(length>2 || length==1){
                    sb.append(" ");
                }
            }
            menu.appendLigne(y,sb);
        }
        return sb.toString();
    }

    /**
     * Affiche une barre de pv, endurence ...
     * @param textColor Couleure du texte a gauche de la barre ex: PV en vert
     * @param text Texte a gauche de la barre ex : PV
     * @param infoColor Couleur des infos au centre de la barre ex : [50/120]
     * @param currentStat Stat courrente a affiché
     * @param maxStat Maximum de la stat courrente a affiché
     * @param barFullColor Couleure de la barre quand pleine
     * @param barEmptyColor Couleure de la barre quand vide
     * @param width Largeure de la barre (sans compter le texte a gauche)
     * @return le String de la barre
     * @author Quentin
     */
    public static String getBarre(String textColor, String text, String infoColor, int currentStat, int maxStat, String barFullColor, String barEmptyColor, String barMoreThanFullColor, int width){
        StringBuilder sb = new StringBuilder();
        sb.append(textColor);
        sb.append(text);
        sb.append(" : ");
        sb.append(infoColor);

        int current_Stat = currentStat;
        LinkedList<Integer> liste_current_Stat = new LinkedList<>();
        while(current_Stat >0){
            liste_current_Stat.add(current_Stat %10);
            current_Stat = current_Stat /10;
        }
        if(liste_current_Stat.size()==0){
            liste_current_Stat.add(0);
        }

        int max_Stat = maxStat;
        LinkedList<Integer> liste_max_Stat = new LinkedList<>();
        while(max_Stat >0){
            liste_max_Stat.add(max_Stat %10);
            max_Stat = max_Stat /10;
        }

        int middle = width/2 - 1;
        int start = middle-(liste_current_Stat.size()+1);
        int end = middle+(liste_max_Stat.size()+1);

        for (int i = 0; i < width; i++) {
            sb.append(i >= ((double)currentStat/(double)maxStat)*width ? barEmptyColor : currentStat > maxStat ? barMoreThanFullColor : barFullColor);
            if(i==start){
                sb.append("[");
            }
            else if(i==end){
                sb.append("]");
            }
            else if(i>start && liste_current_Stat.size()>0){
                sb.append(liste_current_Stat.pollLast());
            }
            else if(i==middle){
                sb.append("/");
            }
            else if(i>start && liste_max_Stat.size()>0){
                sb.append(liste_max_Stat.pollLast());
            }
            else{
                sb.append(" ");
            }
        }
        sb.append(RESET);
        return sb.toString();
    }

    /**
     * Renvoit les touches.
     * @param index La ligne des touches
     * @param beforeWidth le largeur du text avant sur la ligne
     * @return String des touches
     * @author Quentin
     */
    public static String getTouches(int index, int beforeWidth){
        StringBuilder sb = new StringBuilder();
        sb.append(YELLOW).append(BOLD);
        int nbrSpace = 129-beforeWidth;
        if(nbrSpace>=0){
            sb.append(addSpace("",0,nbrSpace));
        }
        else{
            sb.append("\n");
        }
        switch (index){
            case 0 -> {
                sb.append("Deplacement: ");
                sb.append(BRIGTH_GREEN).append("ZQSD");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Mode Tour par tour: ");
                sb.append(BRIGTH_GREEN).append("T");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Attaque à distance: ");
                sb.append(BRIGTH_GREEN).append("A");
            }
            case 1 -> {
                sb.append("Utiliser la potion: ");
                sb.append(BRIGTH_GREEN  ).append("P");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Changement armes: ");
                sb.append(BRIGTH_GREEN).append("I");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Changement potions: ");
                sb.append(BRIGTH_GREEN  ).append("O");
            }
            case 2 -> {
                sb.append("Lacher arme: ");
                sb.append(BRIGTH_GREEN).append("L");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Lacher potion: ");
                sb.append(BRIGTH_GREEN  ).append("M");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Sauvegarder: ");
                sb.append(BRIGTH_GREEN  ).append("W");
                sb.append(YELLOW);
                sb.append("  |  ");
                sb.append("Quitter: ");
                sb.append(BRIGTH_GREEN).append("ESC");
            }
            default -> {}
        }
        sb.append("\n");
        sb.append(RESET);
        return sb.toString();
    }

    /**
     * Affiche les évènements.
     * @author Quentin
     */
    public static void getMessages(){
        StringBuilder sb = new StringBuilder();
        sb.append(PURPLE).append(BOLD).append("-----> ").append(UNDERLINE).append("Evenements:");
        sb.append(RESET);
        sb.append(getTouches(2,getTrueLength(sb.toString())));
        for (String s :TourManager.getMessages()){
            sb.append(BRIGTH_PURPLE).append(s).append("\n");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.print(sb);
    }

    /**
     * Affiche un projectile sur la map
     * @param etage etage
     * @param zone Zone de degats
     * @param style apparence du projectile
     * @author Quentin
     */
    public static void Projectile(Etage etage,ArrayList<Position> zone, Cell.Style style){
        int delay = 1;
        for (Position position : zone) {
            Cell cell = etage.get(position);
            if (cell.getType() != Cell.Style.CellType.PROJECTILE) {
                Cell.Style base_style = cell.getStyle();
                cell.updateCell(cell.isAccesible(), style);
                TourManager.getExecutor().schedule(() -> cell.updateCell(cell.isAccesible(), base_style), 100L * delay, TimeUnit.MILLISECONDS);
            }
            delay++;
        }
    }

    /**
     * Affiche le menu d'accueil du jeu
     * @author Quentin
     */
    public static void start(){
        String marge="                        ";
        String sb = CLEAR +
                "\n\n\n" +
                BRIGTH_BLUE +
                marge + "            $$$$$$$\\  $$\\                                                                                                                                         " + "\n" +
                marge + "            $$  __$$\\ \\__|                                                                                                                                        " + "\n" +
                marge + "            $$ |  $$ |$$\\  $$$$$$\\  $$$$$$$\\  $$\\    $$\\  $$$$$$\\  $$$$$$$\\  $$\\   $$\\  $$$$$$\\         $$$$$$$\\ $$\\   $$\\  $$$$$$\\                               " + "\n" +
                marge + "            $$$$$$$\\ |$$ |$$  __$$\\ $$  __$$\\ \\$$\\  $$  |$$  __$$\\ $$  __$$\\ $$ |  $$ |$$  __$$\\       $$  _____|$$ |  $$ |$$  __$$\\                              " + "\n" +
                marge + "            $$  __$$\\ $$ |$$$$$$$$ |$$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  $$ |$$ |  $$ |$$$$$$$$ |      \\$$$$$$\\  $$ |  $$ |$$ |  \\__|                             " + "\n" +
                marge + "            $$  __$$\\ $$ |$$$$$$$$ |$$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  $$ |$$ |  $$ |$$$$$$$$ |      \\$$$$$$\\  $$ |  $$ |$$ |  \\__|                             " + "\n" +
                marge + "            $$ |  $$ |$$ |$$   ____|$$ |  $$ |  \\$$$  /  $$   ____|$$ |  $$ |$$ |  $$ |$$   ____|       \\____$$\\ $$ |  $$ |$$ |                                   " + "\n" +
                marge + "            $$$$$$$  |$$ |\\$$$$$$$\\ $$ |  $$ |   \\$  /   \\$$$$$$$\\ $$ |  $$ |\\$$$$$$  |\\$$$$$$$\\       $$$$$$$  |\\$$$$$$  |$$ |                                   " + "\n" +
                marge + "            \\_______/ \\__| \\_______|\\__|  \\__|    \\_/     \\_______|\\__|  \\__| \\______/  \\_______|      \\_______/  \\______/ \\__|                                   " + "\n" +
                marge + "                      $$\\                                                                                   $$\\           $$$$$$$$\\ $$$$$$$$\\ $$$$$$$\\  $$$$$$$\\  " + "\n" +
                marge + "                      $$ |                                                                                  $$ |          \\__$$  __|$$  _____|$$  __$$\\ $$  __$$\\ " + "\n" +
                marge + "$$$$$$$\\   $$$$$$\\  $$$$$$\\    $$$$$$\\   $$$$$$\\         $$$$$$\\   $$$$$$\\   $$$$$$\\        $$\\  $$$$$$\\  $$$$$$\\            $$ |   $$ |      $$ |  $$ |$$ |  $$ |" + "\n" +
                marge + "$$  __$$\\ $$  __$$\\ \\_$$  _|  $$  __$$\\ $$  __$$\\       $$  __$$\\ $$  __$$\\ $$  __$$\\       \\__|$$  __$$\\ \\_$$  _|           $$ |   $$$$$\\    $$$$$$$  |$$ |  $$ |" + "\n" +
                marge + "$$ |  $$ |$$ /  $$ |  $$ |    $$ |  \\__|$$$$$$$$ |      $$ /  $$ |$$ |  \\__|$$ /  $$ |      $$\\ $$$$$$$$ |  $$ |             $$ |   $$  __|   $$  __$$< $$ |  $$ |" + "\n" +
                marge + "$$ |  $$ |$$ |  $$ |  $$ |$$\\ $$ |      $$   ____|      $$ |  $$ |$$ |      $$ |  $$ |      $$ |$$   ____|  $$ |$$\\          $$ |   $$ |      $$ |  $$ |$$ |  $$ |" + "\n" +
                marge + "$$ |  $$ |\\$$$$$$  |  \\$$$$  |$$ |      \\$$$$$$$\\       $$$$$$$  |$$ |      \\$$$$$$  |      $$ |\\$$$$$$$\\   \\$$$$  |         $$ |   $$$$$$$$\\ $$ |  $$ |$$$$$$$  |" + "\n" +
                marge + "\\__|  \\__| \\______/    \\____/ \\__|       \\_______|      $$  ____/ \\__|       \\______/       $$ | \\_______|   \\____/          \\__|   \\________|\\__|  \\__|\\_______/ " + "\n" +
                marge + "                                                        $$ |                          $$\\   $$ |                                                                  " + "\n" +
                marge + "                                                        $$ |                          \\$$$$$$  |                                                                  " + "\n" +
                marge + "                                                        \\__|                           \\______/                                                                   " + "\n" +
                "\n\n\n\n\n" +
                BRIGTH_GREEN +
                "1. NOUVELLE PARTIE\n" +
                BRIGTH_YELLOW +
                "2. CHARGER UNE PARTIE\n" +
                BRIGTH_RED +
                "3. QUITTER\n" +
                RESET;
        System.out.println(sb);
    }

    public static void end(){
        Start.setConsoleMode(false);
        int width = 90;
        String timerTxt = "   Temps total:  ";
        String nbrKillTxt = "   Nombre de monstres tuées:  ";
        String nbrKillBossTxt = "   Boss tués:  ";
        String maxEtageTxt = "   Etage maximal atteint:  ";
        String nbrObjetsTxt = "   Nombre total d'objets ramassés:  ";
        System.out.print(
                YELLOW
                + BOLD
                + "┌──────────────────────────────────────────────────────────────────────────────────────────┐\n"
                + "│                                     "
                + BRIGTH_RED
                + ITALIC
                + "FIN DE LA PARTIE"
                + RESET
                + YELLOW
                + BOLD
                + "                                     │\n"
                + "│                                                                                          │\n│"
                + timerTxt
                + BRIGTH_GREEN
                + addSpace(TourManager.getTimer(),timerTxt.length(),width)
                + YELLOW
                + "│\n│"
                + nbrKillTxt
                + BRIGTH_GREEN
                + addSpace(String.valueOf(TourManager.getNbrKill()),nbrKillTxt.length(),width)
                + YELLOW
                + "│\n│"
                + nbrKillBossTxt
                + BRIGTH_GREEN
                + addSpace(String.valueOf(TourManager.getNbrKillBoss()),nbrKillBossTxt.length(),width)
                + YELLOW
                + "│\n│"
                + maxEtageTxt
                + BRIGTH_GREEN
                + addSpace(String.valueOf(Objects.requireNonNull(Start.getMap()).getEtages().size()),maxEtageTxt.length(),width)
                + YELLOW
                + "│\n│"
                + nbrObjetsTxt
                + BRIGTH_GREEN
                + addSpace(String.valueOf(TourManager.getNbrObjetsTotal()),nbrObjetsTxt.length(),width)
                + YELLOW
                + "│\n│                                                                                          │\n"
                + "└──────────────────────────────────────────────────────────────────────────────────────────┘\n"
        );
    }

    /**
     * Ajoute des espaces a la fin du String s.length fois.
     * @param s String
     * @param before_length Longueur du string avant s
     * @param width Largeur totale du string
     * @return La concatenation
     * @author Quentin
     */
    public static String addSpace(String s, int before_length, int width){
        return s + " ".repeat(Math.max(0,width - (s.length() + before_length)));
    }

    /**
     * Renvoit la vrai longueur d'un string sans les characteres ANSI.
     * @param s String dont on cherche la longeure
     * @return Vrai longueure du string
     * @author Quentin
     */
    public static int getTrueLength(String s){
        return s.replaceAll("\u001B\\[[0-9]*m", "").length();
    }

    /**
     * Change le type d'ombre de la map.
     * @param ombre Type d'ombre
     * @author Quentin
     */
    public static void setOmbre(Shadow ombre) {
        Affichage.ombre = ombre;
    }
}
