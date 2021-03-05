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


}
