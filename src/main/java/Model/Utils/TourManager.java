package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.BasicPlayer;
import Model.Main;
import Model.Map.Etage;
import Model.Map.Map;

import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TourManager{
    private static boolean running = true;
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final BufferedReader reader;
    private static BasicPlayer player;
    private final Map map;
    private static Etage etage;

    public TourManager(BufferedReader reader, BasicPlayer player, Map map, Etage etage) {
        this.reader = reader;
        TourManager.player = player;
        this.map = map;
        TourManager.etage = etage;
        schedule();
        Main.affichage(etage);
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

    public void schedule() {
        //executor.scheduleAtFixedRate(() -> Main.affichage(etage), 0, 100, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if(running && m.getPv()>0) m.updateMonster(etage, player);
        },0,m.getUpdate_rate_ms(), TimeUnit.MILLISECONDS);
    }

    public static void pause(){
        running=!running;
    }

    public static void AddMessage(String message){
        if(messages.size()>=10){
            messages.pollFirst();
        }
        messages.add(message);
    }

    public static ArrayDeque<String> getMessages(){
        return messages;
    }
}
