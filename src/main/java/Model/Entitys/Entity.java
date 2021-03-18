package Model.Entitys;

import Model.Cell;
import Model.Map.Etage;
import Model.Position;

public abstract class Entity {
    private Position position;
    private Etage etage;

    public Entity(Etage m, Position pos) {
        position=pos;
        etage =m;
    }

    public Position getPosition() {
        return position.copyOf();
    }

    public void setPosition(Position pos){
        position=pos;
    }

    public Etage getEtage(){
        return etage;
    }

    public void setEtage(Etage etage){
        this.etage=etage;
    }

    public void move(Position pos) {
        Cell cell = etage.get(pos);
        if(cell.isAccesible()){
            cell.setEntity(this);
            etage.get(position).setEntity(null);
            position=pos;
        }
    }

    public void moveLeft() {
        move(position.somme(-1,0));
    }

    public void moveRight() {
        move(position.somme(1,0));
    }

    public void moveUp() {
        move(position.somme(0,-1));
    }

    public void moveDown() {
        move(position.somme(0,1));
    }

    public abstract String toString();
}