package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;


public class Snake extends AbstractMonster{
    private static class Tail extends AbstractMonster{
        protected Tail(Etage m, Position pos, String nom, int pv, int path_type, int lvl) {
            super(m, pos, nom, pv, 0,0,0,60000, path_type, lvl);
        }

        @Override
        public void updateMonster() {}

        @Override
        public String toString() {
            return Affichage.BRIGTH_BLUE+Affichage.BOLD+"O";
        }

        @Override
        public void move(Position pos) {
            if(getEtage().get(pos).getEntity()==null){
                super.move(pos);
            }
        }
    }

    private final ArrayList<Tail> snakeTail = new ArrayList<>();
    private final int size_of_tail;
    protected Snake(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, int path_type, int lvl, int size_of_tail) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        this.size_of_tail=size_of_tail;
        setTail(m,pos,pv,path_type);
    }

    private void setTail(Etage m, Position pos,int pv, int path_type){
        Position random_pos = Procedure.getAccesibleRandomPosition(true, m);
        while (pos.Distance(random_pos)<size_of_tail+1){
            random_pos = Procedure.getAccesibleRandomPosition(true, m);
        }
        ArrayList<Position> astar = Tools.Astar(m, pos, random_pos, path_type);
        for (int i = 0; i < size_of_tail; i++) {
            Tail tail = new Tail(m, astar.get(astar.size() - (2 + i)), "Tail", pv, path_type, lvl);
            snakeTail.add(tail);
            m.addMonster(tail);
        }
    }

    @Override
    public void move(Position pos) {
        Position old_pos = getPosition();
        super.move(pos);
        for (Tail t : snakeTail){
            Position acc = t.getPosition();
            t.move(old_pos);
            old_pos = acc;
        }
    }

    @Override
    public boolean updatePV(int pv) {
        boolean Alive = super.updatePV(pv);
        if(!Alive){
            for(Tail t : snakeTail){
                t.updatePV(-Integer.MAX_VALUE);
            }
        }
        return Alive;
    }

    @Override
    public String toString() {
        return Affichage.BLUE+Affichage.BOLD+"Q";
    }
}
