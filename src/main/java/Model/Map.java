package Model;


import Exceptions.CollisionRoom;

import javax.sql.RowSet;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
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

    public boolean suppX(int x, int y) {
        if (Math.abs(x) > Math.abs(y)) return true;
        return false;
    }

    public boolean sameRoom(Position Init, Position Dest) {
        return false;
    }

    public ArrayList cheminFind(Position posInit, Position posDest) {

        int deltaX;
        int deltaY;
        Position pos = posInit;
        int size;
        size = 5;
        ArrayList<Position> positionList = new ArrayList<>();

        while (pos.getX() != posDest.getX() && pos.getY() != pos.getY()) {

            if (sameRoom(pos, posDest)) {

                deltaX = posDest.getX() - posInit.getX();
                deltaY = posDest.getY() - posInit.getY();

                while (deltaX != 0 && deltaY != 0) {
                    if (deltaX > 0 && deltaY > 0) {
                        if (suppX(deltaX, deltaY)) {
                            deltaX = deltaX - size;
                            pos = new Position(pos.getX() + size, pos.getY());
                            positionList.add(pos);
                        } else {
                            deltaY = deltaY - size;
                            pos = new Position(pos.getX(), pos.getY() + size);
                            positionList.add(pos);
                        }
                    } else if (deltaX > 0 && deltaY < 0) {
                        if (suppX(deltaX, deltaY)) {
                            deltaX = deltaX - size;
                            pos = new Position(pos.getX() + size, pos.getY());
                            positionList.add(pos);
                        } else {
                            deltaY = deltaY + size;
                            pos = new Position(pos.getX(), pos.getY() - size);
                            positionList.add(pos);
                        }
                    } else if (deltaX < 0 && deltaY > 0) {
                        if (suppX(deltaX, deltaY)) {
                            deltaX = deltaX + size;
                            pos = new Position(pos.getX() - size, pos.getY());
                            positionList.add(pos);
                        } else {
                            deltaY = deltaY - size;
                            pos = new Position(pos.getX(), pos.getY() + size);
                            positionList.add(pos);
                        }
                    } else if (deltaX < 0 && deltaY > 0) {
                        if (suppX(deltaX, deltaY)) {
                            deltaX = deltaX + size;
                            pos = new Position(pos.getX() - size, pos.getY());
                            positionList.add(pos);
                        } else {
                            deltaY = deltaY - size;
                            pos = new Position(pos.getX(), pos.getY() + size);
                            positionList.add(pos);
                        }
                    } else if (deltaX < 0 && deltaY < 0) {
                        if (suppX(deltaX, deltaY)) {
                            deltaX = deltaX + size;
                            pos = new Position(pos.getX() - size, pos.getY());
                            positionList.add(pos);
                        } else {
                            deltaY = deltaY + size;
                            pos = new Position(pos.getX(), pos.getY() - size);
                            positionList.add(pos);
                        }

                    }


                }
            }
        }
        return positionList;
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

    public void ligneV2(Position pos1, Position pos2) {
        Position milieu = new Position((pos1.getX() + pos2.getX()) / 2, (pos1.getY() + pos2.getY()) / 2);
        ligne(pos1, milieu);
        ligne(milieu, pos2);
    }

    //TODO a optimiser c'est affreux
    public void RoomFusion(){
        for (int i = 0; i < getRooms().size(); i=i+2) {
            ligneV2(Procedure.getRandomPosition(getRooms().get(i)),Procedure.getRandomPosition(getRooms().get(i+1)));
        }
        for (int i = 0; i < getRooms().size(); i=i+4) {
            ligneV2(Procedure.getRandomPosition(getRooms().get(i)),Procedure.getRandomPosition(getRooms().get(i+3)));
        }

        for (int y = 0; y < getSIZEY(); y++) {
            for (int x = 0; x < getSIZEX(); x++) {
                if (get(x, y).getType().equals(Cell.CellType.VOID)) {
                    Position THIS = new Position(x, y);
                    ArrayList<Position> voisins = new ArrayList<>();
                    voisins.add(THIS.somme(0, -1));
                    voisins.add(THIS.somme(0, 1));
                    voisins.add(THIS.somme(1, 0));
                    voisins.add(THIS.somme(-1, 0));
                    voisins.add(THIS.somme(-1, 1));
                    voisins.add(THIS.somme(1, 1));
                    voisins.add(THIS.somme(1, -1));
                    voisins.add(THIS.somme(-1, -1));
                    for (Position p : voisins) {
                        if (p.getX() >= 0 && p.getY() >= 0 && p.getX() < getSIZEX() && p.getY() < getSIZEY()) {
                            if (get(p).getType().equals(Cell.CellType.NORMAL)) {
                                set(x, y, new Cell(false, Cell.CellType.BORDER));
                            }
                        }
                    }
                }
            }
        }

        for (int y = 0; y < getSIZEY(); y++) {
            for (int x = 0; x < getSIZEX(); x++) {
                if(get(x,y).getType().equals(Cell.CellType.BORDER)){
                    Position THIS = new Position(x,y);
                    ArrayList<Position> voisins = new ArrayList<>();
                    voisins.add(THIS.somme(0,-1));
                    voisins.add(THIS.somme(0,1));
                    voisins.add(THIS.somme(1,0));
                    voisins.add(THIS.somme(-1,0));
                    voisins.add(THIS.somme(-1,1));
                    voisins.add(THIS.somme(1,1));
                    voisins.add(THIS.somme(1,-1));
                    voisins.add(THIS.somme(-1,-1));
                    int nbrVoidVoisins=0;
                    for(Position p : voisins){
                        if(p.getX()>=0 && p.getY()>=0 && p.getX()<getSIZEX() && p.getY()<getSIZEY()){
                            if(get(p).getType().equals(Cell.CellType.VOID)){
                                nbrVoidVoisins++;
                            }
                        }
                        else{
                            nbrVoidVoisins++;
                        }
                    }
                    if(nbrVoidVoisins==0){
                        set(x,y,new Cell(true, Cell.CellType.NORMAL));
                    }
                }
            }
        }
    }
}