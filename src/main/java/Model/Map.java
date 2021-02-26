package Model;


import Exceptions.CollisionRoom;

import java.lang.reflect.Array;
import java.util.*;

public class Map {

    public final static int nbrMaxRooms=5;
    protected int SIZEX;
    protected int SIZEY;
    protected ArrayList<ArrayList<Cell>> Cells;
    protected ArrayList<Room> Rooms = new ArrayList<>();
    protected ArrayList<Entity> Entitys = new ArrayList<>();

    public Map(int SIZEX, int SIZEY) {
        this.SIZEX=SIZEX;
        this.SIZEY=SIZEY;
        fillMap(Cell.CellType.VOID);
    }

    public void fillMap(Cell.CellType type){
        Cells = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                line.add(j, new Cell(false, type));
            }
            Cells.add(i, line);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int x = 0; x < this.SIZEX ; x++) {
            sb.append("\u001B[0m").append(x);
            if(x<10){
                sb.append("  ");
            }
            else{
                sb.append(" ");
            }
        }
        sb.append("\n");
        for (int y = 0; y < this.SIZEY ; y++) {
            if(y<10){
                sb.append("\u001B[0m").append(" ").append(y).append("  ");
            }
            else{
                sb.append("\u001B[0m").append(y).append("  ");
            }
            for (int x = 0; x < this.SIZEX ; x++) {
                sb.append(this.get(x,y)).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addRoom(Room r , Position p){
        if(!isCollision(r,p)){
            for (int y = 0; y < r.getSIZEY() ; y++) {
                for (int x = 0; x < r.getSIZEX() ; x++) {
                    this.set(p.getX() + x, p.getY() + y, r.get(x, y));
                    }
                }
            //TODO a modifier / partie ArrayList Doors
            ArrayList<Position> Doors=new ArrayList<>();
            for (Position pos : r.getDoors()){
                Doors.add(pos.somme(p));
            }
            r.setDoors(Doors);
            Rooms.add(r);
        }
        else{
            throw new CollisionRoom(r);
        }
    }

    public void addEntity(Entity e) {
        Entitys.add(e);
    }

    public void upgradeCellsWithEntitys() {
        for(Entity e : Entitys) {
            Cells.get(e.getPositionY()).get(e.getPositionX()).setEntity(e);
        }
    }

    public boolean isCollision(Room r, Position p){
        for (int y = 0; y < r.getSIZEY() ; y++) {
            for (int x = 0; x < r.getSIZEX() ; x++) {
                if(this.get(p.getX()+x,p.getY()+y).getType() !=  Cell.CellType.VOID
                )return true;
            }
        }
        return false;
    }

    public boolean suppX (int x, int y)
    {
        if (Math.abs(x) > Math.abs(y)) return true;
        return false;
    }

    public boolean sameRoom (Position Init, Position Dest)
    {
        return false;
    }

    public ArrayList cheminFind (Position posInit, Position posDest) {

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

    public Cell get(int x , int y){
        return Cells.get(y).get(x);
    }

    public Cell get(Position pos){
        return get(pos.getX(), pos.getY());
    }

    public void set(int x , int y, Cell c){
        Cells.get(y).set(x,c);
    }

    public void set(Position pos, Cell c){
        set(Cells,pos,c);
    }

    public void set(ArrayList<ArrayList<Cell>> map, Position pos, Cell c){
        map.get(pos.getX()).set(pos.getY(),c);
    }

    public int getSIZEX() {
        return SIZEX;
    }

    public int getSIZEY() {
        return SIZEY;
    }

    public ArrayList<Room> getRooms(){
        return Rooms;
    }

    //TODO c'est moche tkt (les angles marchent pas encore parfaitement
    public void setPath(){
        ArrayList<ArrayList<Cell>> arrayListCell = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList<Cell> line= new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                line.add(get(j,i));
            }
            arrayListCell.add(line);
        }

        for (int y = 0; y < SIZEY; y++) {
            for (int x = 0; x < SIZEX; x++) {
                Position THIS = new Position(x,y);
                Position UP = THIS.somme(0,-1);
                Position DOWN = THIS.somme(0,1);
                Position RIGHT = THIS.somme(1,0);
                Position LEFT = THIS.somme(-1,0);
                Position DOWNofLEFT = THIS.somme(-1,1);
                Position DOWNofRIGHT = THIS.somme(1,1);
                Position UPofRIGHT = THIS.somme(1,-1);
                Position UPofLEFT = THIS.somme(-1,-1);

                if(x==0){
                    LEFT = THIS.somme(0,0);
                    DOWNofLEFT = THIS.somme(1,1);
                    UPofLEFT = LEFT.somme(1,-1);
                }
                if(x==(SIZEX-1)){
                    RIGHT = THIS.somme(0,0);
                    DOWNofRIGHT = THIS.somme(-1,1);
                    UPofRIGHT = RIGHT.somme(-1,-1);
                }
                if(y==0){
                    UP = THIS.somme(0,0);
                    if(x==0){
                        UPofLEFT = LEFT.somme(1,0);
                        UPofRIGHT = RIGHT.somme(2,0);
                    }
                    else if(x==(SIZEX-1)){
                        UPofRIGHT = RIGHT.somme(-1,0);
                        UPofLEFT = LEFT.somme(-2,0);
                    }
                    else{
                        UPofLEFT = LEFT.somme(-1,0);
                        UPofRIGHT = RIGHT.somme(1,0);
                    }
                }
                if(y==(SIZEY-1)){
                    DOWN = THIS.somme(0,0);
                    if(x==0){
                        DOWNofLEFT = THIS.somme(1,0);
                        DOWNofRIGHT = THIS.somme(2,0);
                    }
                    else if(x==(SIZEX-1)){
                        DOWNofLEFT = THIS.somme(-1,0);
                        DOWNofRIGHT = THIS.somme(-2,1);
                    }
                    else{
                        DOWNofLEFT = THIS.somme(-1,0);
                        DOWNofRIGHT = THIS.somme(1,0);
                    }
                }

                if(isWall(THIS) && (canPath(THIS,UP,DOWN,LEFT,RIGHT,UPofLEFT,UPofRIGHT,DOWNofLEFT,DOWNofRIGHT))){
                    set(arrayListCell,new Position(THIS.getY(), THIS.getX()),new Cell(true, Cell.CellType.NORMAL));
                }
            }
        }
        Cells=arrayListCell;
    }

    private boolean isWall(Position pos){
        return (this.get(pos).getType().equals(Cell.CellType.BORDER) || this.get(pos).getType().equals(Cell.CellType.DOOR) || this.get(pos).getType().equals(Cell.CellType.ANGLE));
    }

    private boolean canPath(Position THIS, Position... pos){
        int nbrMursVoisins=0;
        for (Position p : pos){
            if((isWall(p)) && (get(THIS).getType() != Cell.CellType.ANGLE)){
                nbrMursVoisins=nbrMursVoisins+1;
            }
        }
        return nbrMursVoisins > 4;
    }
}

