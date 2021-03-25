import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Map.Map;

import java.util.ArrayList;

public class TourManager {

    private String input;
    private BasicPlayer player;
    private Map map;
    private Etage etage;

    public TourManager(String input, BasicPlayer player, Map map, Etage etage) {
        this.input = input;
        this.player = player;
        this.map = map;
        this.etage = etage;
    }

    public void playTour() {
        processInput();
        processEtage();
        processEntitys();

        etage = map.getCurrent();
    }

    private void processInput() {
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
            e.updateEntity();
        }
    }
}
