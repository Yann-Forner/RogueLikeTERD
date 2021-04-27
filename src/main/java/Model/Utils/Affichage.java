package Model.Utils;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Classe de l'affichage du jeu
 * @author Quentin,Yann
 */
public class Affichage {

    public enum Shadow{
        CIRCLE,RAY,NONE
    }
    private static final Shadow ombre = Shadow.NONE;

    /**
     * Affiche la map
     * @author Quentin
     */
    public static void getMap(){
        Start.setConsoleMode(false);
        System.out.print(Affichage.CLEAR);
        System.out.println(Start.getMap());
        Affichage.getPv();
        Affichage.getTouches();
        Affichage.getMessages();
        Start.setConsoleMode(true);
    }

    /**
     * renvoi l'étage en String
     * @param etage etage courrant
     * @return String de l'étage
     * @author Quentin,Yann
     */
    public static String etage(Etage etage){
        Menu menu = new Menu(etage,Start.getMap());
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append(Affichage.RESET);
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
        if(Start.getPlayer()!=null){
            for(Position p : Tools.getBorder(etage)){
                visibles.addAll(Tools.getVisibles(etage, Start.getPlayer().getPosition(), p));
            }
        }
        //Titre Menu
        sb.append("    ");
        sb.append("                                      ");
        sb.append(Affichage.BRIGTH_YELLOW).append(Affichage.BOLD);
        sb.append("[MENU]");
        sb.append(Affichage.RESET);
        sb.append("                                      ");
        sb.append("\n");

        //Numero cases Map
        for (int y = 0; y < etage.getHeigth(); y++) {
            sb.append(Affichage.RESET);
            if (y < 10) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(y).append(" ");
            }
            //MAP
            for (int x = 0; x < etage.getWidth(); x++) {
                sb.append(Affichage.RESET);
                Position pos = new Position(x,y);
                Cell cell;
                switch (ombre){
                    case CIRCLE -> cell = Start.getPlayer().getPosition().Distance(pos) <= 15 ? etage.get(pos) : new Cell(etage.get(x, y).isAccesible(), new Cell.Style(Cell.Style.CellType.VOID));
                    case RAY -> cell = visibles.contains(pos) ? etage.get(pos) : new Cell(etage.get(x, y).isAccesible(), new Cell.Style(Cell.Style.CellType.VOID));
                    case NONE -> cell = etage.get(pos);
                    default -> throw new IllegalStateException("Unexpected value: " + ombre);
                }
                sb.append(" ").append(cell);
                if(cell.toString().length()>2){
                    sb.append(" ");
                }
            }
            menu.toStringByLine(y,sb);
        }
        return sb.toString();
    }

    /**
     * Affiche les pvs du joueur
     * @author Quentin
     */
    public static void getPv(){
        BasicPlayer player = Start.getPlayer();
        StringBuilder sb = new StringBuilder();
        sb.append(GREEN);
        sb.append("PV : ");
        sb.append(GREY);

        int current_pv = player.getPv();
        LinkedList liste_current_pv = new LinkedList();
        while(current_pv >0){
            liste_current_pv.add(current_pv %10);
            current_pv = current_pv /10;
        }
        if(liste_current_pv.size()==0){
            liste_current_pv.add(0);
        }

        int max_pv = player.getMAX_PV();
        LinkedList liste_max_pv = new LinkedList();
        while(max_pv >0){
            liste_max_pv.add(max_pv %10);
            max_pv = max_pv /10;
        }

        int Width = 120;
        int middle = Width/2 - 1;
        int start = middle-(liste_current_pv.size()+1);
        int end = middle+(liste_max_pv.size()+1);

        for (int i = 0; i < Width; i++) {
            sb.append(i >= ((double)player.getPv()/(double)player.getMAX_PV())*Width ? RED_BACKGROUND : GREEN_BACKGROUND);
            if(i==start){
                sb.append("[");
            }
            else if(i==end){
                sb.append("]");
            }
            else if(i>start && liste_current_pv.size()>0){
                sb.append(liste_current_pv.pollLast());
            }
            else if(i==middle){
                sb.append("/");
            }
            else if(i>start && liste_max_pv.size()>0){
                sb.append(liste_max_pv.pollLast());
            }
            else{
                sb.append(" ");
            }
        }
        sb.append(RESET);
        System.out.print(sb);
    }

    /**
     * Affiche les touches
     * @author Quentin
     */
    public static void getTouches(){
        StringBuilder sb = new StringBuilder();
        sb.append(YELLOW).append(BOLD);
        sb.append("    ");
        sb.append("Deplacement: ");
        sb.append(BRIGTH_GREEN).append("ZQSD");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Mode Tour par tour: ");
        sb.append(BRIGTH_GREEN).append("T");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Inventaire: ");
        sb.append(BRIGTH_GREEN).append("I");
        sb.append("\n                                                                                                                                 ");
        sb.append(YELLOW);
        sb.append("Attaque à distance: ");
        sb.append(BRIGTH_GREEN).append("A");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Objets: ");
        sb.append(BRIGTH_GREEN  ).append("123456789");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Quitter: ");
        sb.append(BRIGTH_GREEN).append("E");
        sb.append(RESET);
        System.out.println(sb);
    }

    /**
     * Affiche les évènements
     * @auhtor Quentin
     */
    public static void getMessages(){
        StringBuilder sb = new StringBuilder();
        sb.append(PURPLE).append(BOLD).append("-----> ").append(UNDERLINE).append("Evenements:");
        sb.append(RESET);
        for (String s :TourManager.getMessages()){
            sb.append("\n").append(BRIGTH_PURPLE).append(s);
        }
        System.out.print(sb);
    }

    /**
     * Affiche un projectile sur la map
     * @param etage etage
     * @param p1 position départ
     * @param p2 position arrivée
     * @param style apparence du projectile
     */
    public static void Projectile(Etage etage,Position p1, Position p2, Cell.Style style){
        int delay = 1;
        ArrayList<Position> ligne = Tools.getLigne(p1, p2);
        for (int i = 0; i < ligne.size(); i++) {
            Cell cell = etage.get(ligne.get(ligne.size()-(i+1)));
            Cell.Style base_style = cell.getStyle();
            cell.updateCell(cell.isAccesible(), style);
            TourManager.getExecutor().schedule(() -> cell.updateCell(cell.isAccesible(), base_style), 50*delay, TimeUnit.MILLISECONDS);
            delay++;
        }
    }

    /**
     * Affiche le menu d'accueil du jeu
     */
    public static void start(){
        StringBuilder sb = new StringBuilder();
        String marge="                        ";
        sb.append(CLEAR);
        sb.append("\n\n\n");
        sb.append(BRIGTH_BLUE);
        sb.append(marge).append("            $$$$$$$\\  $$\\                                                                                                                                         ").append("\n");
        sb.append(marge).append("            $$  __$$\\ \\__|                                                                                                                                        ").append("\n");
        sb.append(marge).append("            $$ |  $$ |$$\\  $$$$$$\\  $$$$$$$\\  $$\\    $$\\  $$$$$$\\  $$$$$$$\\  $$\\   $$\\  $$$$$$\\         $$$$$$$\\ $$\\   $$\\  $$$$$$\\                               ").append("\n");
        sb.append(marge).append("            $$$$$$$\\ |$$ |$$  __$$\\ $$  __$$\\ \\$$\\  $$  |$$  __$$\\ $$  __$$\\ $$ |  $$ |$$  __$$\\       $$  _____|$$ |  $$ |$$  __$$\\                              ").append("\n");
        sb.append(marge).append("            $$  __$$\\ $$ |$$$$$$$$ |$$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  $$ |$$ |  $$ |$$$$$$$$ |      \\$$$$$$\\  $$ |  $$ |$$ |  \\__|                             ").append("\n");
        sb.append(marge).append("            $$  __$$\\ $$ |$$$$$$$$ |$$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  $$ |$$ |  $$ |$$$$$$$$ |      \\$$$$$$\\  $$ |  $$ |$$ |  \\__|                             ").append("\n");
        sb.append(marge).append("            $$ |  $$ |$$ |$$   ____|$$ |  $$ |  \\$$$  /  $$   ____|$$ |  $$ |$$ |  $$ |$$   ____|       \\____$$\\ $$ |  $$ |$$ |                                   ").append("\n");
        sb.append(marge).append("            $$$$$$$  |$$ |\\$$$$$$$\\ $$ |  $$ |   \\$  /   \\$$$$$$$\\ $$ |  $$ |\\$$$$$$  |\\$$$$$$$\\       $$$$$$$  |\\$$$$$$  |$$ |                                   ").append("\n");
        sb.append(marge).append("            \\_______/ \\__| \\_______|\\__|  \\__|    \\_/     \\_______|\\__|  \\__| \\______/  \\_______|      \\_______/  \\______/ \\__|                                   ").append("\n");
        sb.append(marge).append("                      $$\\                                                                                   $$\\           $$$$$$$$\\ $$$$$$$$\\ $$$$$$$\\  $$$$$$$\\  ").append("\n");
        sb.append(marge).append("                      $$ |                                                                                  $$ |          \\__$$  __|$$  _____|$$  __$$\\ $$  __$$\\ ").append("\n");
        sb.append(marge).append("$$$$$$$\\   $$$$$$\\  $$$$$$\\    $$$$$$\\   $$$$$$\\         $$$$$$\\   $$$$$$\\   $$$$$$\\        $$\\  $$$$$$\\  $$$$$$\\            $$ |   $$ |      $$ |  $$ |$$ |  $$ |").append("\n");
        sb.append(marge).append("$$  __$$\\ $$  __$$\\ \\_$$  _|  $$  __$$\\ $$  __$$\\       $$  __$$\\ $$  __$$\\ $$  __$$\\       \\__|$$  __$$\\ \\_$$  _|           $$ |   $$$$$\\    $$$$$$$  |$$ |  $$ |").append("\n");
        sb.append(marge).append("$$ |  $$ |$$ /  $$ |  $$ |    $$ |  \\__|$$$$$$$$ |      $$ /  $$ |$$ |  \\__|$$ /  $$ |      $$\\ $$$$$$$$ |  $$ |             $$ |   $$  __|   $$  __$$< $$ |  $$ |").append("\n");
        sb.append(marge).append("$$ |  $$ |$$ |  $$ |  $$ |$$\\ $$ |      $$   ____|      $$ |  $$ |$$ |      $$ |  $$ |      $$ |$$   ____|  $$ |$$\\          $$ |   $$ |      $$ |  $$ |$$ |  $$ |").append("\n");
        sb.append(marge).append("$$ |  $$ |\\$$$$$$  |  \\$$$$  |$$ |      \\$$$$$$$\\       $$$$$$$  |$$ |      \\$$$$$$  |      $$ |\\$$$$$$$\\   \\$$$$  |         $$ |   $$$$$$$$\\ $$ |  $$ |$$$$$$$  |").append("\n");
        sb.append(marge).append("\\__|  \\__| \\______/    \\____/ \\__|       \\_______|      $$  ____/ \\__|       \\______/       $$ | \\_______|   \\____/          \\__|   \\________|\\__|  \\__|\\_______/ ").append("\n");
        sb.append(marge).append("                                                        $$ |                          $$\\   $$ |                                                                  ").append("\n");
        sb.append(marge).append("                                                        $$ |                          \\$$$$$$  |                                                                  ").append("\n");
        sb.append(marge).append("                                                        \\__|                           \\______/                                                                   ").append("\n");
        sb.append("\n\n\n\n\n");
        sb.append(BRIGTH_GREEN);
        sb.append("1. NOUVELLE PARTIE\n");
        sb.append(BRIGTH_YELLOW);
        sb.append("2. CHARGER UNE PARTIE\n");
        sb.append(BRIGTH_RED);
        sb.append("3. QUITTER\n");
        sb.append(RESET);
        System.out.println(sb);
    }

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

}
