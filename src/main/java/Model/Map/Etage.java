package Model.Map;


import Exceptions.CollisionRoom;
import Model.Entitys.Entity;
import Model.Map.Etage_Strategy.EtageStrategy;
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
    private EtageStrategy strategy;

    protected Etage(int Width, int Heigth) {
        this.Width = Width;
        this.Heigth = Heigth;
        fillMap(new Cell(false,Cell.CellType.VOID));
    }

    public Etage(int Width, int Heigth, EtageStrategy strategy) {
        this(Width,Heigth);
        this.strategy=strategy;
        strategy.composeEtage(this);
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

    public void addRoom(Room r) {
        if (r.noCollision(this)) {
            for (int y = 0; y < r.getHeigth(); y++) {
                for (int x = 0; x < r.getWidth(); x++) {
                    this.set(r.getAbsolutePos().getX() + x, r.getAbsolutePos().getY() + y, r.get(x, y));
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
        Etage etage = new Etage(getWidth(), getHeigth(),getStrategy());
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

    public EtageStrategy getStrategy(){
        return strategy;
    }

    public ArrayList<Room> getRooms() {
        return Rooms;
    }

}