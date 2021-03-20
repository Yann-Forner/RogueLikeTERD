package Model.Map;


import Exceptions.CollisionRoom;
import Model.Entitys.Entity;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

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
        if (r.noCollision(this,p)) {
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

    public Cell get(int x, int y) {
        return Cells.get(y).get(x);
    }

    public Cell get(Position pos) {
        return get(pos.getX(), pos.getY());
    }

    public void set(int x, int y, Cell c) {
        Cells.get(y).set(x, c);
    }

    public Etage copyOf(){
        Etage etage = new Etage(getWidth(), getHeigth());
        for (int y = 0; y < etage.getHeigth(); y++) {
            for (int x = 0; x < etage.getWidth(); x++) {
                etage.Cells.get(y).set(x,get(x,y).copyOf());
            }
        }
        etage.Rooms=Rooms;
        etage.Entitys=Entitys;
        return etage;
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

    public void RoomFusion(){
        //Trace du chemin
        for (int i = 0; i < getRooms().size()-1; i++) {
            Position pos1= Procedure.getRandomPosition(getRooms().get(i));
            Position pos2=Procedure.getRandomPosition(getRooms().get(i+1));
            Position milieu = new Position((pos1.getX() + pos2.getX()) / 2, (pos1.getY() + pos2.getY()) / 2);
            //ligne(pos1, milieu, Cell.CellType.NORMAL);
            //ligne(milieu, pos2, Cell.CellType.NORMAL);
            Tools.ligne(this, pos1, pos2, Cell.CellType.NORMAL,Procedure.getRandomInt(6,0));
            System.out.println(this);
        }

        //Ajout des murs aux chemins
        for (int y = 0; y < getHeigth(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Position pos=new Position(x, y);
                if (get(pos).getType().equals(Cell.CellType.VOID)) {
                    ArrayList<Position> voisins = pos.voisins(this);
                    for (Position p : voisins) {
                        if (get(p).getType().equals(Cell.CellType.NORMAL)) {
                            get(x,y).updateCell(false, Cell.CellType.BORDER);
                        }
                    }
                }
            }
        }
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

}