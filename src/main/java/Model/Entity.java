package Model;

import Model.Map.Map;

public abstract class Entity {
    protected Position position;
    protected final Map map;

    public Entity(Map m,Position pos) {
        position=pos;
        map=m;
    }

    public Position getPosition() {
        return position.copyOf();
    }

    public void move(Position pos) {
        Cell cell = map.get(pos);
        if(cell.isAccesible()){
            cell.setEntity(this);
            map.get(position).setEntity(null);
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
}