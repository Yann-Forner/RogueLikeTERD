import Model.Affichage;
import Model.BasicPlayer;
import Model.Map;
import Model.Procedure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final int MapWidth = 40;
    public static final int MapHeigth = 40;

    public static void main(String[] args) throws IOException {
        Map myMap = new Map(MapWidth,MapHeigth);
        //Model.Procedure.setRamdomRooms(myMap);
        //myMap.addEntity(new BasicPlayer(new Position(11, 16), myMap));
        //myMap.upgradeCellsWithEntitys();
        //System.out.println(myMap.cheminFind(myMap.getRooms().get(0).getDoors().get(0),myMap.getRooms().get(1).getDoors().get(0)));
        //myMap.setPath();

        Procedure.setRandomRooms(myMap);
        Affichage.map(myMap);
        myMap.RoomFusion();
        Procedure.setRandomMob(myMap);
        Procedure.setRandomChest(myMap,3);
        Affichage.map(myMap);

        //Affichage.Rooms(myMap);

        /*
        myMap.ObstaclesAleatoires(300);

        Position pos1=Procedure.getAccesibleRandomPosition(myMap);
        Position pos2=Procedure.getAccesibleRandomPosition(myMap);

        long t1 = System.currentTimeMillis();
        ArrayList<Position> astar = myMap.Astar(pos1,pos2);
        System.out.println(System.currentTimeMillis()-t1 +" ms");
        Affichage.Path(myMap,astar);
         */

        BasicPlayer player = new BasicPlayer(myMap,Procedure.getAccesibleRandomPosition(myMap));
        myMap.addEntity(player);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean Game = false;
        while(Game){
            Affichage.map(myMap);
            System.out.print("Enter your key: ");
            switch (reader.readLine()){
                case "z" :
                    player.moveUp();
                    break;
                case "q" :
                    player.moveLeft();
                    break;
                case "s" :
                    player.moveDown();
                    break;
                case "d" :
                    player.moveRight();
                    break;
                case "exit" :
                    System.exit(0);
                default:
                    System.out.println("Wrong key");
            }
        }

    }
}
