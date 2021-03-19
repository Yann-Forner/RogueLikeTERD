package Model.Utils;

import Model.Map.Etage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Tools {

    public static ArrayList<Position> Astar(Etage etage, Position depart, Position arrive, int pathType){
        class Noeud extends Position{
            public double heuristique=0;
            public double cout=0;
            public Noeud cameFrom=null;

            public Noeud(int x, int y) {
                super(x, y);
            }

            @Override
            public double Distance(Position pos) {
                return (int)(super.Distance(pos)*10);
            }

            private double getScore(){
                return cout+heuristique;
            }

            public Noeud copyOf(){
                Noeud noeud = new Noeud(this.getX(), this.getY());
                noeud.cout=this.cout;
                noeud.cameFrom=this.cameFrom;
                noeud.heuristique=this.heuristique;
                return noeud;
            }

            private ArrayList<Noeud> getStandardNeighboors(){
                ArrayList<Noeud> voisins = new ArrayList<>();
                voisins.add(new Noeud(getX() - 1,getY()));
                voisins.add(new Noeud(getX() + 1,getY()));
                voisins.add(new Noeud(getX(),getY() - 1));
                voisins.add(new Noeud(getX(),getY() + 1));
                return voisins;
            }

            private ArrayList<Noeud> getDiagonalNeighboors(){
                ArrayList<Noeud> voisins = new ArrayList<>();
                voisins.add(new Noeud(getX() - 1,getY() - 1));
                voisins.add(new Noeud(getX() - 1,getY() + 1));
                voisins.add(new Noeud(getX() + 1,getY() + 1));
                voisins.add(new Noeud(getX() + 1,getY() - 1));
                return voisins;
            }
        }

        ArrayList<Noeud> closedList = new ArrayList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue<>(Comparator.comparingDouble(Noeud::getScore));

        openList.add(new Noeud(depart.getX(),depart.getY()));

        while(openList.size()>0){
            Noeud u = openList.poll();

            //Current == arrive
            if(u.equals(arrive)) {
                ArrayList<Position> chemin = new ArrayList<>();
                chemin.add(u.copyOf());
                Noeud current = u;
                while(current.cameFrom!=null){
                    current=current.cameFrom;
                    chemin.add(current.copyOf());
                }
                return chemin;
            }

            // Génération des voisins
            ArrayList<Noeud> voisins;
            switch (pathType) {
                case 0:
                    voisins = u.getStandardNeighboors();
                    break;
                default:
                    voisins = u.getDiagonalNeighboors();
                    break;
            }
            // Filtrage pour obtenir un voisin valide (dans la map, accessible...)
            voisins = voisins.stream().filter(p -> ((p.getX() >= 0 && p.getY() >= 0 && p.getX() < etage.getWidth() && p.getY() < etage.getHeigth()) && etage.get(p.getX(),p.getY()).isAccesible())).collect(Collectors.toCollection(ArrayList::new));

            //Parcous voisins
            for(Noeud n : voisins){
                n.cameFrom=u;
                n.cout = u.cout + n.Distance(u);
                n.heuristique = n.Distance(arrive);

                if (!(closedList.contains(n)) || n.getScore() < u.getScore()){
                    if(openList.contains(n)){
                        openList.remove(n);
                        openList.add(n);
                    }
                    else {
                        openList.add(n);
                    }
                }
            }
            closedList.add(u);
        }
        return new ArrayList<>();
    }


}
