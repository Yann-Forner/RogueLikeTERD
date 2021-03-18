package Model.Map;


import Exceptions.CollisionRoom;
import Model.*;
import Model.Entitys.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class Etage {
    protected int Width;
    protected int Heigth;
    public ArrayList<ArrayList<Cell>> Cells;
    protected ArrayList<Room> Rooms = new ArrayList<>();
    protected ArrayList<Entity> Entitys = new ArrayList<>();

    public Etage(int Width, int Heigth) {
        this.Width = Width;
        this.Heigth = Heigth;
        fillMap(new Cell(false,Cell.CellType.VOID));
    }

    public void setCells(ArrayList<ArrayList<Cell>> cells) {
        Cells = cells;
    }

    public void fillMap(Cell c) {
        Cells = new ArrayList<>();
        for (int i = 0; i < getHeigth(); i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < getWidth(); j++) {
                line.add(j, c.copyOf());
            }
            Cells.add(i, line);
        }
    }

    @Override
    public String toString() {
        return Affichage.etage(this);
    }

    public void addRoom(Room r, Position p) {
        if (!isCollision(r, p)) {
            for (int y = 0; y < r.getHeigth(); y++) {
                for (int x = 0; x < r.getWidth(); x++) {
                    this.set(p.getX() + x, p.getY() + y, r.get(x, y));
                }
            }
            Rooms.add(r);
        } else {
            throw new CollisionRoom(r);
        }
    }

    public void addEntity(Entity e) {
        get(e.getPosition()).setEntity(e);
        Entitys.add(e);
    }

    public boolean isCollision(Room r, Position p) {
        for (int y = 0; y < r.getHeigth(); y++) {
            for (int x = 0; x < r.getWidth(); x++) {
                if (this.get(p.getX() + x, p.getY() + y).getType() != Cell.CellType.VOID
                ) return true;
            }
        }
        return false;
    }

    public Cell get(int x, int y) {
        return Cells.get(y).get(x);
    }

    public Cell get(Position pos) {
        return get(pos.getX(), pos.getY());
    }

    public void set(int x, int y, Cell c) {
        Cells.get(y).set(x, c);
    }


    public int getWidth() {
        return Width;
    }

    public int getHeigth() {
        return Heigth;
    }

    public ArrayList<Room> getRooms() {
        return Rooms;
    }

    public void ligne(Position p1, Position p2) {
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
            get(p).updateCell(true, Cell.CellType.NORMAL);
        }
    }

    public void RoomFusion(){
        //Trace du chemin
        for (int i = 0; i < getRooms().size()-1; i++) {
            Position pos1=Procedure.getRandomPosition(getRooms().get(i));
            Position pos2=Procedure.getRandomPosition(getRooms().get(i+1));
            Position milieu = new Position((pos1.getX() + pos2.getX()) / 2, (pos1.getY() + pos2.getY()) / 2);
            ligne(pos1, milieu);
            ligne(milieu, pos2);
        }

        //Ajout des murs aux chemins
        for (int y = 0; y < getHeigth(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Position pos=new Position(x, y);
                System.out.println(get(pos).getType());
                if (get(pos).getType().equals(Cell.CellType.VOID)) {
                    ArrayList<Position> voisins = pos.voisins(this);
                    for (Position p : voisins) {
                        if (get(p).getType().equals(Cell.CellType.NORMAL)) {
                            System.out.println("---------------------------------------");
                            get(x,y).updateCell(false, Cell.CellType.BORDER);
                        }
                    }
                }
            }
        }
        System.out.println(this);
        //Suppression des murs inutiles
        for (int y = 0; y < getHeigth(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Position pos=new Position(x, y);
                ArrayList<Position> voisins = pos.voisins(this);
                if(voisins.size()>6){
                    boolean isUseless=true;
                    for(Position p : voisins){
                        if(get(p).getType().equals(Cell.CellType.VOID)){
                            isUseless=false;
                            break;
                        }
                    }
                    if(isUseless){
                        get(pos).updateCell(true, Cell.CellType.NORMAL);
                    }
                }
            }
        }
    }

    public ArrayList<Position> Astar(Position depart, Position arrive, PathType pathType){

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
            ArrayList<Noeud> voisins = new ArrayList<>();
            if (pathType.equals(PathType.CROSS)) addStandardNeighboors(voisins, u);
            else if (pathType.equals(PathType.DIAGONAL)) addDiagoNeighboors(voisins, u);

            /* Filtrage pour obtenir un voisin valide (dans la map, accessible...) */
            voisins = voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < getWidth() && p.getY() < getHeigth()) && get(p.getX(),p.getY()).isAccesible())).collect(Collectors.toCollection(ArrayList::new));

            //Parcous voisins
            for(Noeud n : voisins){
                n.cameFrom=u;
                n.cout = u.cout + n.Distance(u);
                n.heuristique = n.Distance(arrive);

                if (!(closedList.contains(n)) || n.getScore() < u.getScore()){
                    if(openList.contains(n)){
                        openList.remove(n);
                        openList.add(n);
                    }
                    openList.add(n);
                }
            }
            closedList.add(u);
        }
        return new ArrayList<>();
    }

    private void addStandardNeighboors(ArrayList<Noeud> voisins, Noeud u) {
        voisins.add(new Noeud(u.getX() - 1,u.getY()));
        voisins.add(new Noeud(u.getX() + 1,u.getY()));
        voisins.add(new Noeud(u.getX(),u.getY() - 1));
        voisins.add(new Noeud(u.getX(),u.getY() + 1));
    }

    private void addDiagoNeighboors(ArrayList<Noeud> voisins, Noeud u) {
        voisins.add(new Noeud(u.getX() - 1,u.getY() - 1));
        voisins.add(new Noeud(u.getX() - 1,u.getY() + 1));
        voisins.add(new Noeud(u.getX() + 1,u.getY() + 1));
        voisins.add(new Noeud(u.getX() + 1,u.getY() - 1));
    }

    public enum PathType {
        CROSS, DIAGONAL;
    }

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
    }

}