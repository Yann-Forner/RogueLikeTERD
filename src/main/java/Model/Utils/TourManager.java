package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TourManager implements Serializable {
    private static boolean running = true;
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final BasicPlayer player;
    private final Map map;

    public TourManager(BasicPlayer player) {
        this.player = player;
        map = new Map(player);
    }

    public void playTour(BufferedReader reader){
        Affichage.getMap(map);
        try {
            processInput(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        processEtage();
        if(!running){
            for(AbstractMonster m : player.getEtage().getMonsters()){
                m.updateMonster();
            }
        }
    }

    private void processInput(String input) throws IOException {
        if(input.length()>0){
            switch (input.charAt(0)) {
                case 'z' , 'Z' -> player.moveUp();
                case 'q' , 'Q' -> player.moveLeft();
                case 's' , 'S' -> player.moveDown();
                case 'd' , 'D' -> player.moveRight();
                case 't' , 'T' -> TourParTour();
                case 'i' , 'I' -> player.getInventory().switchWeapons(); //Inventaire
                case 'p' , 'P' -> player.getInventory().switchPotions(); // Potions
                case 'a' , 'A' -> System.out.println("A"); //Attaque distance
                case 'w' , 'W' -> Start.sauvegarde();
                case '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9'  -> System.out.println("Nombre"); //Objets
                case 'e' , 'E' , 3 -> Start.end();
            }
        }
    }

    private void processEtage() {
        switch (map.getCurrent().get(player.getPosition()).getType()) {
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
        executor.scheduleAtFixedRate(() -> Affichage.getMap(map), 0, 300, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running && m.getPv()>0 && m.getEtage().equals(Objects.requireNonNull(Start.getPlayer()).getEtage())){
                m.updateMonster();
            }
        }, 10, m.getUpdate_rate_ms(), TimeUnit.MILLISECONDS);
    }

    public static void TourParTour(){
        TourManager.addMessage(running ? "Le jeu est en mode Tour par tour" : "Le jeu n'est plus en mode Tour par tour");
        running=!running;
    }

    public static void addMessage(String message){
        if(messages.size()>8){
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

    public BasicPlayer getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }
}
