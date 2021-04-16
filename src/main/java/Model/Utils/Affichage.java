package Model.Utils;

import Model.Entitys.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Map.Room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Affichage {
    public enum Shadow{
        CIRCLE,RAY,NONE;
    }
    private static final Shadow ombre = Shadow.NONE;

    public static void getMap(Map map){
        System.out.print(Affichage.CLEAR);
        System.out.println(map.getCurrent());
        int index = map.getIndexCurrent()+1;
        //TODO ça va pas marcher du coup #YANN
        Affichage.print_txt_in_menu(index == 0 ? "Salle secrete" : "Etage n°" + index+"\n");
        Affichage.getPv();
        Affichage.getTouches();
        Affichage.getMessages();
    }

    public static String etage(Etage etage){
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
            //Menu
            sb.append(Affichage.BOLD).append(Affichage.BLUE).append("     ");
            if(y==0){
                sb.append("╔═════════════════════════════════════════════════════════════════════════════════╗");
            }
            else if(y==etage.getHeigth()-1){
                sb.append("╚═════════════════════════════════════════════════════════════════════════════════╝");
            }
            else{
                if (y==7 && Start.getPlayer()!= null){
                    String equipement = "Equipement : ";
                    if (Start.getPlayer().getInventory().getCurrentWeapon()!=null)equipement+=Start.getPlayer().getInventory().getCurrentWeapon().getNom()+"/";
                    else equipement+="X/";
                    if (Start.getPlayer().getInventory().getCurrentArmure()!=null)equipement+=Start.getPlayer().getInventory().getCurrentArmure().getNom();
                    else equipement+="X";
                    sb.append(print_txt_in_menu(equipement));
                }else {
                    sb.append("║                                                                                 ║");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String print_txt_in_menu(String message){//81
        StringBuilder sb = new StringBuilder();
        if(message.length()<81){
            sb.append("║");
            int spacing = (81 - message.length())/2;
            sb.append(" ".repeat(spacing));
            sb.append(message);
            if((message.length()%2==0))++spacing;
            sb.append(" ".repeat(spacing));
            sb.append("║");
        }else if (message.length()>81){
            sb.append("║                                                                                 ║");
        }
        else {
            sb.append("║").append(message).append("║");
        }

        return sb.toString();
    }

    public static void getPv(){
        BasicPlayer player = Start.getPlayer();
        int pv = player.getPv();
        StringBuilder sb = new StringBuilder();
        sb.append(GREEN);
        sb.append("PV : ");
        sb.append(GREY);
        sb.append(GREEN_BACKGROUND);
        for (int i = 0; i < 100; i++) {
            if(i>=pv){
                sb.append(RED_BACKGROUND);
            }
            switch (i){
                case 45 -> sb.append(pv == 100 ? "[" : " ");
                case 46 -> sb.append(pv == 100 ? "1" : pv < 10 ? " " : "[");
                case 47 -> sb.append(pv >= 10 ? (pv%100)/10 : "[");
                case 48 -> sb.append(pv%10);
                case 49 -> sb.append("/");
                case 50 -> sb.append("1");
                case 51, 52 -> sb.append("0");
                case 53 -> sb.append("]");
                default -> sb.append(" ");
            }
        }
        sb.append(RESET);

        sb.append(BLUE).append("   LVL: ").append(player.getLvl());
        sb.append(" -> ").append("[");
        sb.append(BRIGTH_BLUE).append(player.getCURRENT_EXP());
        sb.append(BLUE).append("/");
        sb.append(BRIGTH_BLUE).append(player.getMAX_EXP());
        sb.append(BLUE).append("]");
        System.out.print(sb);
    }

    public static void getTouches(){
        StringBuilder sb = new StringBuilder();
        sb.append(YELLOW).append(BOLD);
        sb.append("     ");
        sb.append("Deplacement: ");
        sb.append(BRIGTH_GREEN).append("ZQSD");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Pause: ");
        sb.append(BRIGTH_GREEN).append("P");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Inventaire: ");
        sb.append(BRIGTH_GREEN).append("I");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Attaque à distance: ");
        sb.append(BRIGTH_GREEN).append("A");
        sb.append("\n                                                                                                                                 ");
        sb.append(YELLOW);
        sb.append("Objets: ");
        sb.append(BRIGTH_GREEN  ).append("123456789");
        sb.append(YELLOW);
        sb.append("  |  ");
        sb.append("Quitter: ");
        sb.append(BRIGTH_GREEN).append("E");
        sb.append(RESET);
        System.out.println(sb);
    }

    public static void getMessages(){
        StringBuilder sb = new StringBuilder();
        sb.append(PURPLE).append(BOLD).append("-----> ").append(UNDERLINE).append("Evenements:");
        sb.append(RESET);
        for (String s :TourManager.getMessages()){
            sb.append("\n").append(BRIGTH_PURPLE).append(s);
        }
        System.out.print(sb);
    }

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
