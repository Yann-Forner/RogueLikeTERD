package Model;


import Exceptions.CollisionRoom;

import java.io.File;
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
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int x = 0; x < this.SIZEX; x++) {
            sb.append("\u001B[0m").append(x);
            if (x < 10) {
                sb.append("  ");
            } else {
                sb.append(" ");
            }
        }
        sb.append("\n");
        for (int y = 0; y < this.SIZEY; y++) {
            if (y < 10) {
                sb.append("\u001B[0m").append(" ").append(y).append("  ");
            } else {
                sb.append("\u001B[0m").append(y).append("  ");
            }
            for (int x = 0; x < this.SIZEX; x++) {
                sb.append(this.get(x, y)).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addRoom(Room r, Position p) {
        if (!isCollision(r, p)) {
            for (int y = 0; y < r.getSIZEY(); y++) {
                for (int x = 0; x < r.getSIZEX(); x++) {
                    this.set(p.getX() + x, p.getY() + y, r.get(x, y));
                }
            }
            //TODO a modifier / partie ArrayList Doors
            ArrayList<Position> Doors = new ArrayList<>();
            for (Position pos : r.getDoors()) {
                Doors.add(pos.somme(p));
            }
            r.addDoors(Doors);
            Rooms.add(r);
        } else {
            throw new CollisionRoom(r);
        }
    }

    public void addEntity(Entity e) {
        Entitys.add(e);
    }

    public void upgradeCellsWithEntitys() {
        for (Entity e : Entitys) {
            Cells.get(e.getPositionY()).get(e.getPositionX()).setEntity(e);
        }
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

    public void set(Position pos, Cell c) {
        set(Cells, pos, c);
    }

    public void set(ArrayList<ArrayList<Cell>> map, Position pos, Cell c) {
        map.get(pos.getX()).set(pos.getY(), c);
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

    public void ligne(Position pos1, Position pos2) {
        //TODO svp quelqu'un peut me dire pourquoi il faut inverser ?
        Position p1 = new Position(pos1.getY(), pos1.getX());
        Position p2 = new Position(pos2.getY(), pos2.getX());
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
            set(p, new Cell(true, Cell.CellType.NORMAL));
        }
    }

    //TODO a optimiser c'est affreux
    public void RoomFusion(){
        for (int i = 0; i < getRooms().size()-1; i++) {
            Position pos1=Procedure.getRandomPosition(getRooms().get(i));
            Position pos2=Procedure.getRandomPosition(getRooms().get(i+1));
            Position milieu = new Position((pos1.getX() + pos2.getX()) / 2, (pos1.getY() + pos2.getY()) / 2);
            ligne(pos1, milieu);
            ligne(milieu, pos2);
        }

        for (int y = 0; y < getSIZEY(); y++) {
            for (int x = 0; x < getSIZEX(); x++) {
                if (get(x, y).getType().equals(Cell.CellType.VOID)) {
                    ArrayList<Position> voisins = voisins(new Position(x, y));
                    for (Position p : voisins) {
                        if (get(p).getType().equals(Cell.CellType.NORMAL)) {
                            set(x, y, new Cell(false, Cell.CellType.BORDER));
                        }
                    }
                }
            }
        }

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
                        set(x,y,new Cell(true, Cell.CellType.NORMAL));
                    }
                }
            }
        }
    }

    public ArrayList<Position> Astar(Position depart, Position arrive){
        class Noeud extends Position{
            public int heuristique=0;
            public int cout=0;

            public Noeud(int x, int y) {
                super(x, y);
            }

            @Override
            public String toString() {
                return "Noeud{" +
                        "heuristique=" + heuristique +
                        ", cout=" + cout +
                        ", " + super.toString() +
                        '}';
            }

            @Override
            public int Distance(Position pos) {
                double d = Math.sqrt(Math.pow((pos.getX() - getX()), 2) + Math.pow((pos.getY() - getY()), 2));
                return (int)d*10;
            }

            private int getScore(){
                return cout+heuristique;
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }
        }

        ArrayList<ArrayList<Noeud>> map = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList<Noeud> noeudArrayList = new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                noeudArrayList.add(new Noeud(j,i));
            }
            map.add(noeudArrayList);
        }

        HashMap<Noeud,Noeud> cameFrom = new HashMap<>();
        ArrayList<Noeud> closedList = new ArrayList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue((o1, o2) -> {
            Noeud n1 = (Noeud)o1;
            Noeud n2= (Noeud)o2;
            if(n1.getScore() < n2.getScore()){
                return -1;
            }
            else if(n1.getScore() == n2.getScore()){
                return 0;
            }
            else{
                return 1;
            }
        });

        openList.add(map.get(depart.getY()).get(depart.getX()));

        while(openList.size()>0){
            System.out.println(openList.size());
            Noeud u = openList.poll();
            if(u.getX()==arrive.getX() && u.getY()==arrive.getY()) {
                ArrayList<Position> chemin = new ArrayList<>();
                chemin.add(new Position(u.getX(),u.getY()));
                Noeud current = u;
                while (cameFrom.containsKey(current)){
                    current = cameFrom.get(current);
                    chemin.add(new Position(current.getX(),current.getY()));
                }
                return chemin;
            }

            ArrayList<Noeud> voisins = new ArrayList<>();
            if(u.getX()>0){
                if(get(u.getX()-1,u.getY()).isAccesible()){
                    voisins.add(map.get(u.getY()).get(u.getX()-1));
                }
            }
            if(u.getX()<SIZEX-1){
                if(get(u.getX()+1,u.getY()).isAccesible()) {
                    voisins.add(map.get(u.getY()).get(u.getX() + 1));
                }
            }
            if(u.getY()>0){
                if(get(u.getX(),u.getY()-1).isAccesible()) {
                    voisins.add(map.get(u.getY() - 1).get(u.getX()));
                }
            }
            if(u.getY()<SIZEY-1){
                if(get(u.getX(),u.getY()+1).isAccesible()) {
                    voisins.add(map.get(u.getY() + 1).get(u.getX()));
                }
            }


            for(Noeud n : voisins){
                if (!(closedList.contains(n) || (openList.contains(n) && n.cout<u.cout))){
                    cameFrom.put(n,u);
                    openList.remove(n);
                    n.cout = u.cout + 1;
                    n.heuristique = n.cout + n.Distance(arrive);
                    openList.add(n);
                }
            }
            closedList.add(u);
        }
        System.out.println("Aucun chemin entre: "+depart + " et " + arrive);
        return new ArrayList<>();
    }

}