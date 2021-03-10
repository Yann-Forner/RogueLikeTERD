package Model;


import Exceptions.CollisionRoom;

import java.util.*;
import java.util.stream.Collectors;

public class Map {

    public final static int nbrMaxRooms = 8;
    protected int SIZEX;
    protected int SIZEY;
    protected ArrayList<ArrayList<Cell>> Cells;
    protected ArrayList<Room> Rooms = new ArrayList<>();
    protected ArrayList<Entity> Entitys = new ArrayList<>();

    public Map(int SIZEX, int SIZEY) {
        this.SIZEX = SIZEX;
        this.SIZEY = SIZEY;
        fillMap(new Cell(false,Cell.CellType.VOID));
    }

    public void fillMap(Cell c) {
        Cells = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                line.add(j, new Cell(c.isAccesible(), c.getType()));
            }
            Cells.add(i, line);
        }
    }

    @Override
    public String toString() {
        return Affichage.map(this);
    }

    public void addRoom(Room r, Position p) {
        if (!isCollision(r, p)) {
            for (int y = 0; y < r.getSIZEY(); y++) {
                for (int x = 0; x < r.getSIZEX(); x++) {
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
        for (int y = 0; y < r.getSIZEY(); y++) {
            for (int x = 0; x < r.getSIZEX(); x++) {
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

    public int getSIZEX() {
        return SIZEX;
    }

    public int getSIZEY() {
        return SIZEY;
    }

    public ArrayList<Room> getRooms() {
        return Rooms;
    }

    public ArrayList<Position> voisins(Position pos){
        ArrayList<Position> voisins = new ArrayList<>();
        voisins.add(pos.somme(0, -1));
        voisins.add(pos.somme(0, 1));
        voisins.add(pos.somme(1, 0));
        voisins.add(pos.somme(-1, 0));
        voisins.add(pos.somme(-1, 1));
        voisins.add(pos.somme(1, 1));
        voisins.add(pos.somme(1, -1));
        voisins.add(pos.somme(-1, -1));
        return voisins.stream().filter(p -> (p.getX() >= 0 && p.getY() >= 0 && p.getX() < getSIZEX() && p.getY() < getSIZEY())).collect(Collectors.toCollection(ArrayList::new));
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
            //set(p, new Cell(true, Cell.CellType.NORMAL));
            get(p).updateCell(true, Cell.CellType.NORMAL);
        }
    }

    //TODO a optimiser c'est affreux
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
        for (int y = 0; y < getSIZEY(); y++) {
            for (int x = 0; x < getSIZEX(); x++) {
                if (get(x, y).getType().equals(Cell.CellType.VOID)) {
                    ArrayList<Position> voisins = voisins(new Position(x, y));
                    for (Position p : voisins) {
                        if (get(p).getType().equals(Cell.CellType.NORMAL)) {
                            //set(x, y, new Cell(false, Cell.CellType.BORDER));
                            get(x,y).updateCell(false, Cell.CellType.BORDER);
                        }
                    }
                }
            }
        }

        //Suppression des murs inutiles
        for (int y = 0; y < getSIZEY(); y++) {
            for (int x = 0; x < getSIZEX(); x++) {
                if(get(x,y).getType().equals(Cell.CellType.BORDER)){
                    ArrayList<Position> voisins = voisins(new Position(x, y));
                    int nbrVoidVoisins=0;
                    for(Position p : voisins){
                        if(get(p).getType().equals(Cell.CellType.VOID)){
                            nbrVoidVoisins++;
                        }
                    }
                    nbrVoidVoisins=nbrVoidVoisins+(8-voisins.size());
                    if(nbrVoidVoisins==0){
                        //set(x,y,new Cell(true, Cell.CellType.NORMAL));
                        get(x,y).updateCell(true, Cell.CellType.NORMAL);
                    }
                }
            }
        }
    }

    public ArrayList<Position> Astar(Position depart, Position arrive){
        class Noeud extends Position{
            public int heuristique=0;
            public int cout=0;
            public Noeud cameFrom=null;

            public Noeud(int x, int y) {
                super(x, y);
            }

            public int Distance(Position pos) {
                double d = Math.sqrt(Math.pow((pos.getX() - getX()), 2) + Math.pow((pos.getY() - getY()), 2));
                return (int)(d*10);
            }

            private int getScore(){
                return cout+heuristique;
            }
        }

        ArrayList<Noeud> closedList = new ArrayList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue<>(Comparator.comparingInt(Noeud::getScore));

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

                for(Noeud n : closedList){
                    set(n.getX(),n.getY(),new Cell(true, Cell.CellType.PATH){
                        @Override
                        public String toString() {
                            return Affichage.BRIGTH_BLUE+"X";
                        }
                    });
                }
                for(Noeud n : openList){
                    set(n.getX(),n.getY(),new Cell(true, Cell.CellType.PATH){
                        @Override
                        public String toString() {
                            return Affichage.BRIGTH_GREEN+"X";
                        }
                    });
                }

                return chemin;
            }

            //Voisins
            ArrayList<Noeud> voisins = new ArrayList<>();
            voisins.add(new Noeud(u.getX()-1,u.getY()));
            voisins.add(new Noeud(u.getX()+1,u.getY()));
            voisins.add(new Noeud(u.getX(),u.getY()-1));
            voisins.add(new Noeud(u.getX(),u.getY()+1));
            voisins = voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < getSIZEX() && p.getY() < getSIZEY()) && get(p.getX(),p.getY()).isAccesible())).collect(Collectors.toCollection(ArrayList::new));

            //Parcous voisins
            for(Noeud n : voisins){
                if (!(closedList.contains(n) || (openList.contains(n) && n.cout<u.cout))){
                    n.cameFrom=u;
                    n.cout = u.cout + n.Distance(u);
                    n.heuristique = n.cout + n.Distance(arrive);
                    openList.add(n);
                }
            }
            closedList.add(u);
        }
        return new ArrayList<>();
    }

    public Map copyOf(){
        Map map = new Map(SIZEX, SIZEY);
        map.Cells=new ArrayList<>(Cells);
        map.Rooms=new ArrayList<>(Rooms);
        map.Entitys=new ArrayList<>(Entitys);
        return map;
    }


    public void ObstaclesAleatoires(int nbr){
        fillMap(new Cell(true, Cell.CellType.NORMAL));
        for (int i = 0; i < nbr; i++) {
            Position randomPosition = Procedure.getAccesibleRandomPosition(this);
            //set(randomPosition.getX(),randomPosition.getY(),new Cell(false, Cell.CellType.BORDER));
            get(randomPosition.getX(),randomPosition.getY()).updateCell(false, Cell.CellType.BORDER);
        }
    }

}