package Model;

import Model.*;

import java.util.ArrayList;

public class Affichage {

    public static void map(Map map){
        System.out.println(map);
        for (Room r: map.getRooms()) {
            //System.out.println(r);
        }
    }

    public static void voisins(Position p, Map map){
        Map m = new Map(3,3);
        ArrayList<Position> voisins=new ArrayList<>();
        voisins.add(p.somme(-1,-1));
        voisins.add(p.somme(0,-1));
        voisins.add(p.somme(1,-1));
        voisins.add(p.somme(-1,0));
        voisins.add(p);
        voisins.add(p.somme(1,0));
        voisins.add(p.somme(-1,1));
        voisins.add(p.somme(0,1));
        voisins.add(p.somme(1,1));
        for (int i = 0; i < voisins.size(); i++) {
            m.set(i%3,i/3,map.get(voisins.get(i)));
        }
        System.out.println(p);
        System.out.println(m);
    }

    public static void Rooms(Map map){
        int acc=0;
        for (int k = 0; k < map.getRooms().size(); k++) {
            Room r = map.getRooms().get(k);
            for (int i = 1; i < r.getSIZEY()-1; i++) {
                for (int j = 1; j < r.getSIZEX()-1; j++) {
                    int finalAcc = acc;
                    int finalK = k;
                    map.set(r.getAbsolutePos().getX()+j,r.getAbsolutePos().getY()+i,new Cell(true, Cell.CellType.PATH){
                        @Override
                        public String toString() {
                            return "\u001B[3"+ finalAcc +"m"+ finalK;
                        }
                    });
                }
            }
            acc++;
        }
        Affichage.map(map);
    }

    public static void Path(Map map,ArrayList<Position> path){
        for (int i = 0; i < path.size(); i++) {
            Position p = path.get(i);
            if(i==0){
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return "\u001B[0mD";
                    }
                });
            }
            else if(i==path.size()-1){
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return "\u001B[0mA";
                    }
                });
            }
            else{
                map.set(p.getX(),p.getY(),new Cell(true, Cell.CellType.PATH){
                    @Override
                    public String toString() {
                        return "\u001B[0m°";
                    }
                });
            }
        }
        Affichage.map(map);
    }

}

