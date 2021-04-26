package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TourManager implements Serializable {
    private static boolean running = System.getProperty("os.name").equals("Linux");
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static int Tour = 0;
    private final BasicPlayer player;
    private final Map map;

    public TourManager(BasicPlayer player) {
        this.player = player;
        map = new Map(player);
    }

    public void playTour(BufferedReader reader){
        Affichage.getMap();
        try {
            processInput(reader);
        } catch (IOException e) {
            Start.setConsoleMode(false);
            e.printStackTrace();
        }
        processEtage();
        if(!running){
            Tour++;
            for(AbstractMonster m : player.getEtage().getMonsters()){
                m.updateMonster();
                if(Tour % Math.ceil((float) m.getUpdate_rate_ms() /300)==0){
                   m.updateMonster();
                }
            }
        }
    }

    private void processInput(BufferedReader reader) throws IOException {
        char cmd = 0;
        if(System.console()!=null){
            char[] input = System.console().readPassword();
            if(input.length>0){
                cmd = input[0];
                
            }
        }
        else{
            String input = reader.readLine();
            if(input.length()>0){
                cmd = input.charAt(0);
            }
        }
        switch (cmd){
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
            default -> processInput(reader);
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
        executor.scheduleAtFixedRate(() -> {
            if(running){
                Affichage.getMap();
            }
        }, 0, 300, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running){
                if(m.getPv()>0 && m.getEtage().equals(Objects.requireNonNull(Start.getPlayer()).getEtage())) {
                    m.updateMonster();
                }
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
