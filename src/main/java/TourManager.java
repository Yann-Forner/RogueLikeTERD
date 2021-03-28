import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Map.Map;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TourManager{

    private final BufferedReader reader;
    private final BasicPlayer player;
    private final Map map;
    private Etage etage;

    public TourManager(BufferedReader reader, BasicPlayer player, Map map, Etage etage) {
        this.reader = reader;
        this.player = player;
        this.map = map;
        this.etage = etage;
        schedule();
    }

    public void playTour() {
        try{
            processInput(reader.readLine());
        }
        catch (Exception e){}
        processEtage();
        etage = map.getCurrent();
        Main.affichage(etage);
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

    public void schedule() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        //executor.scheduleAtFixedRate(this::Main.affichage(etage), 0, 200, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(this::processEntitys,0,1,TimeUnit.SECONDS);
    }
}
