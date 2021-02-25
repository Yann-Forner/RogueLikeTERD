package Model;


import Exceptions.CollisionRoom;

import java.util.ArrayList;

public class Map {

protected int SIZEX;
protected int SIZEY;
protected ArrayList<ArrayList<Cell>> Cells;
protected ArrayList<Room> Rooms = new ArrayList<>();

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
        Rooms.add(r);
        if(!isCollision(r,p)){
        for (int y = 0; y < r.getSIZEY() ; y++) {
            for (int x = 0; x < r.getSIZEX() ; x++) {
                this.set(p.getX()+x, p.getY()+y ,r.get(x,y) );
            }
        }
        }else{
//            throw new CollisionRoom(r);
            System.out.println("COLLISION ROOM");
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
