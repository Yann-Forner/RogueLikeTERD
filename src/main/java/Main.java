import Model.*;import Model.Map.Etage;import Model.Map.Map;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Main {    public static void main(String[] args) throws IOException {        Procedure.setSeed();        Map map = new Map();        BasicPlayer player = map.getPlayer();        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));        boolean Game = true;        while(Game){            Etage myEtage=map.getCurrent();            System.out.println(Affichage.CLEAR+myEtage);            System.out.print(Affichage.RESET+"Etage n°"+(map.getIndexCurrent()+1));            System.out.println("\n\n\n\n\n\n\n\n\n\n");            System.out.print(Affichage.RESET+"Enter your key: ");            switch (reader.readLine()) {                case "z" -> player.moveUp();                case "q" -> player.moveLeft();                case "s" -> player.moveDown();                case "d" -> player.moveRight();                case "exit" -> System.exit(0);                default -> System.out.println("Wrong key");            }            switch (myEtage.get(map.getPlayer().getPosition()).getType()){                case UP :                    map.UP();                    break;                case DOWN :                    map.DOWN();                    break;                default:                    break;            }        }    }}