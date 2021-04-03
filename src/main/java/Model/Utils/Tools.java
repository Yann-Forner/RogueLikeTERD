package Model.Utils;

import Model.Main;
import Model.Map.Cell;
import Model.Map.Etage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Tools {
    public static final int PATH_CROSS = 0;
    public static final int PATH_DIAG = 1;
    public static final int PATH_GHOST = 2;

    /**
     * Algoritme A* qui permet de trouver le plus court chemin entre la Position de depart et celle d'arrivé.
     * @param etage Etage
     * @param depart Position
     * @param arrive Position
     * @param pathType int : 0->Normal 1->Diagonales
     * @return ArrayList<Position>
     */
    public static ArrayList<Position> Astar(Etage etage, Position depart, Position arrive, int pathType){
        class Noeud extends Position{
            public double heuristique=0;
            public double cout=0;
            public Noeud cameFrom=null;

            public Noeud(int x, int y) {
                super(x, y);
            }

            @Override
            public double Distance(Position pos) {
                return (int)(super.Distance(pos)*10);
            }

            private double getScore(){
                return cout+heuristique;
            }

            public Noeud copyOf(){
                Noeud noeud = new Noeud(this.getX(), this.getY());
                noeud.cout=this.cout;
                noeud.cameFrom=this.cameFrom;
                noeud.heuristique=this.heuristique;
                return noeud;
            }

            private double CoutPerType(Cell.Style.CellType type){
                if(pathType==-1){
                    return switch (type) {
                        case BORDER -> 1000;
                        case VOID -> 100;
                        default -> 0;
                    };
                }
                return 0;
            }

            private ArrayList<Noeud> getStandardNeighboors(){
                ArrayList<Noeud> voisins = new ArrayList<>();
                voisins.add(new Noeud(getX() - 1,getY()));
                voisins.add(new Noeud(getX() + 1,getY()));
                voisins.add(new Noeud(getX(),getY() - 1));
                voisins.add(new Noeud(getX(),getY() + 1));
                // Filtrage pour obtenir un voisin valide (dans la map, accessible...)
                return voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < etage.getWidth() && p.getY() < etage.getHeigth()) && etage.get(p.getX(),p.getY()).isAccesible())).collect(Collectors.toCollection(ArrayList::new));
            }

            private ArrayList<Noeud> getDiagonalNeighboors(){
                ArrayList<Noeud> voisins = new ArrayList<>();
                voisins.add(new Noeud(getX() - 1,getY() - 1));
                voisins.add(new Noeud(getX() - 1,getY() + 1));
                voisins.add(new Noeud(getX() + 1,getY() + 1));
                voisins.add(new Noeud(getX() + 1,getY() - 1));
                // Filtrage pour obtenir un voisin valide (dans la map, accessible...)
                return voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < etage.getWidth() && p.getY() < etage.getHeigth()) && etage.get(p.getX(),p.getY()).isAccesible())).collect(Collectors.toCollection(ArrayList::new));
            }

            private ArrayList<Noeud> getNoClipStandardNeighboors(){
                ArrayList<Noeud> voisins = new ArrayList<>();
                voisins.add(new Noeud(getX() - 1,getY()));
                voisins.add(new Noeud(getX() + 1,getY()));
                voisins.add(new Noeud(getX(),getY() - 1));
                voisins.add(new Noeud(getX(),getY() + 1));
                // Filtrage pour obtenir un voisin valide (dans la map, accessible...)
                return voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < etage.getWidth() && p.getY() < etage.getHeigth()))).collect(Collectors.toCollection(ArrayList::new));
            }
        }

        ArrayList<Noeud> closedList = new ArrayList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue<>(Comparator.comparingDouble(Noeud::getScore));

        openList.add(new Noeud(depart.getX(),depart.getY()));

        while(openList.size()>0){
            Noeud u = openList.poll();

            //Current == arrive
            if(u.equals(arrive)) {
                ArrayList<Position> chemin = new ArrayList<>();
                chemin.add(u.copyOf());
                Noeud current = u;
                while(current.cameFrom!=null){
                    current=current.cameFrom;
                    chemin.add(current.copyOf());
                }
                return chemin;
            }

            // Génération des voisins
            ArrayList<Noeud> voisins = switch (pathType) {
                case 0 -> u.getStandardNeighboors();
                case 1 -> u.getDiagonalNeighboors();
                default -> u.getNoClipStandardNeighboors();
            };

            //Parcous voisins
            for(Noeud n : voisins){
                n.cameFrom=u;
                n.cout = u.cout + n.Distance(u) + n.CoutPerType(etage.get(n).getType());
                n.heuristique = n.Distance(arrive);

                if (!(closedList.contains(n)) || n.getScore() < u.getScore()){
                    openList.remove(n);
                    openList.add(n);
                }
            }
            closedList.add(u);
        }
        return new ArrayList<>();
    }

    public static ArrayList<Position> getBorder(Etage etage){
        ArrayList<Position> bordure = new ArrayList<>();
        for (int y = 0; y < etage.getHeigth(); y++) {
            bordure.add(new Position(0,y));
            bordure.add(new Position(etage.getWidth()-1,y));
            for (int x = 1; x < etage.getWidth()-1; x++) {
                if(y==0 || y==etage.getHeigth()-1){
                    bordure.add(new Position(x,y));
                }
            }
        }
        return bordure;
    }

    /**
     * Trace une ligne simple qui ignore tous les obstacles entre la Position p1 et la Position p2,
     * le nombre de virage depend du degree.
     * @param etage Etage
     * @param p1 Position
     * @param p2 Posiiton
     * @param type Cell.Style.CellType
     * @param degree int
     */
    public static void ligne(Etage etage, Position p1, Position p2, Cell.Style.CellType type, int degree){
        if(degree==0){
            ligne(etage, p1, p2, type);
        }
        else{
            Position milieu = new Position((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
            if(degree>1){
                ligne(etage, p1, milieu, type,degree-1);
                ligne(etage, milieu, p2, type,degree-1);
            }
            else{
                ligne(etage, p1, milieu, type);
                ligne(etage, milieu, p2, type);
            }
        }
    }

    private static void ligne(Etage etage, Position p1, Position p2, Cell.Style.CellType type) {
        Cell.Style style = new Cell.Style(type);
        ArrayList<Position> chemin = new ArrayList<>();
        chemin.add(p1);
        Position lastPos = chemin.get(chemin.size() - 1);
        while (lastPos.getX() != p2.getX()) {
            if (lastPos.getX() > p2.getX()) {
                chemin.add(new Position(lastPos.getX() - 1, lastPos.getY()));
            } else if (lastPos.getX() < p2.getX()) {
                chemin.add(new Position(lastPos.getX() + 1, lastPos.getY()));
            }
            lastPos = chemin.get(chemin.size() - 1);
        }
        while (lastPos.getY() != p2.getY()) {
            lastPos = chemin.get(chemin.size() - 1);
            if (lastPos.getY() > p2.getY()) {
                chemin.add(new Position(lastPos.getX(), lastPos.getY() - 1));
            } else if (lastPos.getY() < p2.getY()) {
                chemin.add(new Position(lastPos.getX(), lastPos.getY() + 1));
            }
        }
        for (Position p : chemin) {
            etage.get(p).updateCell(true, style);
        }
    }

    public static ArrayList<Position> getVisibles(Etage etage,Position p1, Position p2){
        ArrayList<Position> chemin = new ArrayList<>();
        int x,y,xe,ye;
        int dx = p2.getX()-p1.getX();
        int dy = p2.getY()- p1.getY();
        int dx1 = Math.abs(dx);
        int dy1 = Math.abs(dy);
        int px = 2*dy1-dx1;
        int py = 2*dx1-dy1;

        if(dy1<=dx1){
            if(dx>=0) {
                x = p1.getX();
                y = p1.getY();
                xe = p2.getX();
            }
            else {
                x = p2.getX();
                y = p2.getY();
                xe = p1.getX();
            }
            chemin.add(new Position(x,y));
            while(x<xe){
                x=x+1;
                if(px<0) {
                    px=px+2*dy1;
                }
                else {
                    if((dx<0 && dy<0) || (dx>0 && dy>0)) {
                        y=y+1;
                    }
                    else {
                        y=y-1;
                    }
                    px=px+2*(dy1-dx1);
                }
                chemin.add(new Position(x,y));
            }
        }
        else {
            if (dy >= 0) {
                x = p1.getX();
                y = p1.getY();
                ye = p2.getY();
            } else {
                x = p2.getX();
                y = p2.getY();
                ye = p1.getY();
            }
            chemin.add(new Position(x,y));
            while (y < ye){
                y = y + 1;
                if (py <= 0) {
                    py = py + 2 * dx1;
                } else {
                    if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
                        x = x + 1;
                    } else {
                        x = x - 1;
                    }
                    py = py + 2 * (dx1 - dy1);
                }
                chemin.add(new Position(x,y));
            }
        }
        chemin.sort((o1, o2) -> {
            Position pos = Main.getPlayer().getPosition();
            return Double.compare(o1.Distance(pos), o2.Distance(pos));
        });
        ArrayList<Position> visibles = new ArrayList<>();
        for(Position position : chemin) {
            visibles.add(position);
            if (etage.get(position).getType().equals(Cell.Style.CellType.BORDER)) {
                break;
            }
        }
        return visibles;
    }

}
