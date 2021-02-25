package Model;


import Exceptions.CollisionRoom;

import java.util.ArrayList;
import java.util.HashMap;

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
        fillMap();
    }

    public void fillMap(){
        Cells = new ArrayList<>();
        for (int i = 0; i < SIZEY; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZEX; j++) {
                line.add(j, new Cell(false, Cell.CellType.VOID));
            }
            Cells.add(i, line);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < this.SIZEY ; y++) {
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
                    this.set(p.getX()+x, p.getY()+y ,r.get(x,y) );
                }
            }
            //TODO a modifier
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

    public void set(int x , int y, Cell c){
        Cells.get(y).set(x,c);
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
}
