package Model.Utils;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;

import java.util.ArrayList;

public class Affichage {

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
        sb.append("\n");
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
                Cell cell = etage.get(x, y);
                if(cell.toString().length()>2){
                    sb.append(" ").append(cell).append(" ");
                }
                else{
                    sb.append(" ").append(cell);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void Rooms_Color(Etage etage){
        int acc=0;
        for (int k = 0; k < etage.getRooms().size(); k++) {
            Room r = etage.getRooms().get(k);
            for (int i = 0; i < r.getHeigth(); i++) {
                for (int j = 0; j < r.getWidth(); j++) {
                    int finalAcc = acc;
                    int finalK = k;
                    etage.set(r.getAbsolutePos().getX()+j,r.getAbsolutePos().getY()+i,new Cell(true, Cell.CellType.PATH){
                        @Override
                        public String toString() {
                            return "\u001B[3"+ finalAcc +"m"+ finalK;
                        }
                    });
                }
            }
            acc++;
        }
        Affichage.etage(etage);
    }

    public static void Path(Etage etage, ArrayList<Position> path){
        for (int i = 0; i < path.size(); i++) {
            Position p = path.get(i);
            if(i==0){
                etage.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return Affichage.BRIGTH_YELLOW+"A";
                    }
                });
            }
            else if(i==path.size()-1){
                etage.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return Affichage.BRIGTH_YELLOW+"D";
                    }
                });
            }
            else{
                etage.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return Affichage.BRIGTH_PURPLE+"X";
                    }
                });
            }
        }
        Affichage.etage(etage);
    }

    public static void Palette(){
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int code = i * 16 + j;
                String space="";
                for (int k = 0; k < 3-String.valueOf(code).length(); k++) {
                    space=space+' ';
                }
                System.out.print("\u001b[38;5;" + code + "m " + space + code);
            }
            System.out.println(Affichage.RESET+"\n");
        }
        System.out.println("\n");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int code = i * 16 + j;
                String space="";
                for (int k = 0; k < 3-String.valueOf(code).length(); k++) {
                    space=space+' ';
                }
                System.out.print("\u001b[48;5;" + code + "m " + space + code);
            }
            System.out.println(Affichage.RESET+"\n");
        }
        System.out.println(Affichage.RESET+"\n");
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


