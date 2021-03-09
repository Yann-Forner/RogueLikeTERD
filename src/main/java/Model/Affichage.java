package Model;

import Model.*;

import java.util.ArrayList;

public class Affichage {
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

    public static void map(Map map){
        System.out.println(map);
    }

    public static void voisins(Position p, Map map){
        Map m = new Map(3,3);
        ArrayList<Position> voisins=new ArrayList<>();
        voisins.add(p.somme(-1,-1));
        voisins.add(p.somme(0,-1));
        voisins.add(p.somme(1,-1));
        voisins.add(p.somme(-1,0));
        voisins.add(p);
        voisins.add(p.somme(1,0));
        voisins.add(p.somme(-1,1));
        voisins.add(p.somme(0,1));
        voisins.add(p.somme(1,1));
        for (int i = 0; i < voisins.size(); i++) {
            m.set(i%3,i/3,map.get(voisins.get(i)));
        }
        System.out.println(p);
        System.out.println(m);
    }

    public static void Rooms(Map map){
        int acc=0;
        for (int k = 0; k < map.getRooms().size(); k++) {
            Room r = map.getRooms().get(k);
            for (int i = 1; i < r.getSIZEY()-1; i++) {
                for (int j = 1; j < r.getSIZEX()-1; j++) {
                    int finalAcc = acc;
                    int finalK = k;
                    map.set(r.getAbsolutePos().getX()+j,r.getAbsolutePos().getY()+i,new Cell(true, Cell.CellType.PATH){
                        @Override
                        public String toString() {
                            return "\u001B[3"+ finalAcc +"m"+ finalK;
                        }
                    });
                }
            }
            acc++;
        }
        Affichage.map(map);
    }

    public static void Path(Map map,ArrayList<Position> path){
        for (int i = 0; i < path.size(); i++) {
            Position p = path.get(i);
            if(i==0){
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return RESET+'D';
                    }
                });
            }
            else if(i==path.size()-1){
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return RESET+'A';
                    }
                });
            }
            else{
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return RESET+'°';
                    }
                });
            }
        }
        Affichage.map(map);
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

}

