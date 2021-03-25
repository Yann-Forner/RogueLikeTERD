import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Affichage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class TourManager {

    private BufferedReader reader;
    private BasicPlayer player;
    private Map map;
    private Etage etage;

    public TourManager(BufferedReader reader, BasicPlayer player, Map map, Etage etage) {
        this.reader = reader;
        this.player = player;
        this.map = map;
        this.etage = etage;
    }

    public void playTour() {
        try{
            processInput(reader.readLine());
        }
        catch (Exception e){}
        processEtage();
        processEntitys();
        etage = map.getCurrent();
        Main.affichage();
    }

    private void processInput(String input){
        switch (input) {
            case "z" , "\u001B[A" -> player.moveUp();
            case "q" , "\u001B[D" -> player.moveLeft();
            case "s" , "\u001B[B" -> player.moveDown();
            case "d" , "\u001B[C" -> player.moveRight();
            case "exit" -> System.exit(0);
            default -> System.out.println("Wrong key:"+input);
        }
    }

    private void processEtage() {
        switch (etage.get(map.getPlayer().getPosition()).getType()) {
            case UP :
                map.UP();
                break;
            case DOWN :
                map.DOWN();
                break;
            case TRAP_ROOM :
                map.TRAP_ROOM();
                break;
            default:
                break;
        }
    }

    private void processEntitys() {
        ArrayList<Entity> entitys = etage.getEntitys();

        for (Entity e : entitys) {
            e.updateEntity(etage, player);
        }
    }
}
