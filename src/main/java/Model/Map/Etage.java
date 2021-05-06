package Model.Map;


import Exceptions.CollisionRoom;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

import java.io.Serializable;
import java.util.*;

/**
 * Un Etage est un enssemble de rooms reliés entre elles.
 * @author Yann, Quentin, JP
 */
public class Etage implements Serializable {
    protected int Width;
    protected int Heigth;
    private ArrayList<ArrayList<Cell>> Cells;
    protected ArrayList<Room> Rooms = new ArrayList<>();
    protected ArrayList<AbstractMonster> Monsters = new ArrayList<>();
    protected ArrayList<AbstractItem> Items = new ArrayList<>();
    private Position up;
    private Position down;

    /**
     * Crée un etage selon ces dimensions.
     * @param Width Largeur
     * @param Heigth Hauteur
     * @author Yann
     */
    protected Etage(int Width, int Heigth) {
        this.Width = Width;
        this.Heigth = Heigth;
        fillMap(new Cell(false,new Cell.Style(Cell.Style.CellType.VOID)));
    }

    /**
     * Crée un etage selon ces dimensions et la strategy.
     * @param Width Largeur
     * @param Heigth Hauteur
     * @param strategy Strategy
     * @param etageDepart determine si c'est le premier ateg de la map
     * @author Yann
     */
    public Etage(int Width, int Heigth, EtageStrategy strategy, boolean etageDepart) {
        this(Width,Heigth);
        strategy.composeEtage(this, etageDepart);
    }

    /**
     * Rempli l'etage avec des copies de la cellule c.
     * @param c cell
     * @author Yann
     */
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

    /**
     * Ajoute la room a l'etage si possible.
     * @param r Room
     * @author Yann
     */
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

    /**
     * Ajoute le monstre a l'etage.
     * @param m Monstre
     * @author Quentin
     */
    public void addMonster(AbstractMonster m) {
        get(m.getPosition()).setEntity(m);
        Monsters.add(m);
    }

    /**
     * Enleve de monstre de l'etage.
     * @param m Monstre
     * @author Quentin
     */
    public void removeMonster(AbstractMonster m) {
        get(m.getPosition()).setEntity(null);
        Monsters.remove(m);
    }

    /**
     * Ajoute l'item a l'etage.
     * @param i Item
     * @author JP
     */
    public void addItem(AbstractItem i) {
        get(i.getPosition()).setEntity(i);
        Items.add(i);
    }

    /**
     * Enleve l'item de l'etage.
     * @param i Item
     * @author JP
     */
    public void removeItem(AbstractItem i) {
        get(i.getPosition()).setEntity(null);
        Items.remove(i);
    }

    /**
     * Ajoute une cellule piegé a l'etage.
     * @author Quentin
     */
    public void setTrapCell() {
        Position accesibleRandomPosition = Procedure.getAccesibleRandomPosition(false, this);
        get(accesibleRandomPosition).updateCell(true, new Cell.Style(Cell.Style.CellType.TRAP_ROOM));
    }

    /**
     * Renvoit La cellule aux coordonées x y.
     * @param x int
     * @param y int
     * @return Cell
     * @author Yann
     */
    public Cell get(int x, int y) {
        return Cells.get(y).get(x);
    }

    /**
     * Renvoit La cellule a la Position pos.
     * @param pos Position
     * @return Cell
     * @author Quentin
     */
    public Cell get(Position pos) {
        return get(pos.getX(), pos.getY());
    }

    /**
     * Change la cellule aux coordonées x y.
     * @param x int
     * @param y int
     * @param c Cell
     * @author Yann
     */
    public void set(int x, int y, Cell c) {
        Cells.get(y).set(x, c);
    }

    /**
     * Renvoit la largeur de l'etage.
     * @return int
     * @author Yann
     */
    public int getWidth() {
        return Width;
    }

    /**
     * Renvoit la hauteur de l'etage.
     * @return int
     * @author Yann
     */
    public int getHeigth() {
        return Heigth;
    }

    /**
     * Renvoit la liste des monstres.
     * @return ArrayList<AbstractMonster>
     * @author Yann
     */
    public ArrayList<AbstractMonster> getMonsters() {
        return Monsters;
    }

    /**
     * Renvoit la liste des rooms.
     * @return ArrayList<Rooms>
     * @author Yann
     */
    public ArrayList<Room> getRooms() {
        return Rooms;
    }

    /**
     * Renvoit la liste des cellules.
     * @return ArrayList<ArrayList<Cell>>
     * @author Yann
     */
    public ArrayList<ArrayList<Cell>> getCells(){
        return Cells;
    }

    /**
     * Renvoit la liste des items.
     * @return ArrayList<AbstractItems>
     * @author JP
     */
    public ArrayList<AbstractItem> getItems() {
        return Items;
    }

    /**
     * Renvoit la position de la cellule pour monter.
     * @return Position
     * @author Quentin
     */
    public Position getUp() {
        return up;
    }

    /**
     * Renvoit la position de la cellule pour descendre.
     * @return Position
     * @author Quentin
     */
    public Position getDown() {
        return down;
    }

    /**
     * Defini la position de la cellule pour monter.
     * @param up Position
     * @author Quentin
     */
    public void setUp(Position up) {
        this.up = up;
    }

    /**
     * Defini la position de la cellule pour descendre.
     * @param down Position
     * @author Quentin
     */
    public void setDown(Position down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return Affichage.etage(this);
    }
}