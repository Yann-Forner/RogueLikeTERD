package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

import java.util.ArrayList;


public class Snake extends AbstractMonster{
    private ArrayList<Tail> snakeTail = new ArrayList<>();
    protected Snake(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int size_of_tail) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type);
        int posX = pos.getX();
        int posY = pos.getY();
        for (int i = 0; i <size_of_tail ; i++) {
            if (m.get(posX-1,posY).isAccesible() && m.get(posX-1,posY).getEntity() == null )posX-=1;
            else if (m.get(posX,posY-1).isAccesible() && m.get(posX,posY-1).getEntity() == null )posY-=1;
            else if (m.get(posX,posY+1).isAccesible() && m.get(posX,posY+1).getEntity() == null )posY+=1;
            else posX+=1;
            snakeTail.add(new Tail(m, new Position(posX,posY), "Tail", pv, force, vision_radius, agro, update_rate_ms, path_type));
        }
    }

    @Override
    public void move(Position pos) {
       Position oldPos = this.getPosition().copyOf();
       super.move(pos);
       moveTail(oldPos);

    }

    private void moveTail(Position p ){

    }

    @Override
    public String toString() {
        return Affichage.BLUE+Affichage.BOLD+"Q";
    }
}
