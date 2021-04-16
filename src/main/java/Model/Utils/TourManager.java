package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Map.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
        Start.affichage(etage);
    }

    public void playTour() {
        try {
            processInput(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        processEtage();
        etage = map.getCurrent();
        Start.affichage(etage);
    }

    private void processInput(String input){
        switch (input) {
            case "z" , "\u001B[A" -> player.moveUp();
            case "q" , "\u001B[D" -> player.moveLeft();
            case "s" , "\u001B[B" -> player.moveDown();
            case "d" , "\u001B[C" -> player.moveRight();
            case "p" -> pause();
            case "exit" -> System.exit(0);
            default -> System.out.println("Wrong key:"+input);
        }
    }

    private void processEtage() {
        switch (etage.get(player.getPosition()).getType()) {
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
        executor.scheduleAtFixedRate(() -> Start.affichage(etage), 0, 100, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running && m.getPv()>0 && m.getEtage().equals(player.getEtage())) m.updateMonster();
        }, 0, m.getUpdate_rate_ms(), TimeUnit.MILLISECONDS);
    }

    public static void pause(){
        TourManager.addMessage("Le jeu est en pause");
        running=!running;
    }

    public static void addMessage(String message){
        if(messages.size()>=10){
            messages.pollFirst();
        }
        messages.add(message);
    }

    public static ArrayDeque<String> getMessages(){
        return messages;
    }

    public static ScheduledExecutorService getExecutor(){
        return executor;
    }
}
