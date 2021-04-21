package Model.Map;


import Exceptions.CollisionRoom;
import Model.Entitys.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import org.json.JSONObject;

import java.util.*;

public class Etage {
    protected int Width;
    protected int Heigth;
    private  ArrayList<ArrayList<Cell>> Cells;
    //TODO supprimer arraylist rooms
    protected ArrayList<Room> Rooms = new ArrayList<>();
    protected ArrayList<AbstractMonster> Monsters = new ArrayList<>();
    protected ArrayList<AbstractItem> Items = new ArrayList<>();

    protected Etage(int Width, int Heigth) {
        this.Width = Width;
        this.Heigth = Heigth;
        fillMap(new Cell(false,new Cell.Style(Cell.Style.CellType.VOID)));
    }

    public Etage(int Width, int Heigth, EtageStrategy strategy) {
        this(Width,Heigth);
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
                    set(r.getAbsolutePos().getX() + x, r.getAbsolutePos().getY() + y, r.get(x, y));
                }
            }
            Rooms.add(r);
        } else {
            throw new CollisionRoom(r);
        }

    }

    public void addMonster(AbstractMonster m) {
        get(m.getPosition()).setEntity(m);
        Monsters.add(m);
    }

    public void removeMonster(AbstractMonster m) {
        get(m.getPosition()).setEntity(null);
        Monsters.remove(m);
    }

    public void addItem(AbstractItem i) {
        get(i.getPosition()).setEntity(i);
        Items.add(i);
    }

    public void removeItem(AbstractItem i) {
        get(i.getPosition()).setEntity(null);
        Items.remove(i);
    }

    public void setTrapCell() {
        Position accesibleRandomPosition = Procedure.getAccesibleRandomPosition(false, this);
        get(accesibleRandomPosition).updateCell(true, new Cell.Style(Cell.Style.CellType.TRAP_ROOM));
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

    public ArrayList<AbstractMonster> getMonsters() {
        return Monsters;
    }

    public ArrayList<Room> getRooms() {
        return Rooms;
    }

    public ArrayList<ArrayList<Cell>> getCells(){
        return Cells;
    }

    public ArrayList<AbstractItem> getItems() {
        return Items;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("Width",Width);
        json.put("Heigth",Heigth);
        for (int i = 0; i < Heigth; i++) {
            for (int j = 0; j < Width; j++) {
                JSONObject cell = new JSONObject();
                cell.put("position",new Position(j,i).toJSON());
                cell.put("cell",get(j,i).toJSON());
                json.append("Cells",cell);
            }
        }
        for(Room r : Rooms){
            json.append("Rooms",r.toJSON());
        }
        for(AbstractMonster m : Monsters){
            json.append("Monsters",m.toJSON());
        }
        for(AbstractItem i : Items){
            json.append("Items",i.toJSON());
        }
        return json;
    }
}