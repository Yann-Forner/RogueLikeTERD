package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.BasicPlayer;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Etage;
import Model.Map.Map;

import java.io.BufferedReader;
import java.io.IOException;
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
        this.map = map;
        TourManager.player = player;
        TourManager.etage = etage;
        schedule();
        Affichage.getMap(map);
    }

    public void playTour() {
        try {
            processInput(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        processEtage();
        etage = map.getCurrent();
        if(!running){
            for(AbstractMonster m : player.getEtage().getMonsters()){
                m.updateMonster();
            }
        }
        Affichage.getMap(map);
    }

    private void processInput(String input){
        switch (input) {
            case "z" , "Z" , "\u001B[A" -> player.moveUp();
            case "q" , "Q" , "\u001B[D" -> player.moveLeft();
            case "s" , "S" , "\u001B[B" -> player.moveDown();
            case "d" , "D" , "\u001B[C" -> player.moveRight();
            case "t" , "T" -> TourParTour();
            case "i" , "I" -> System.out.println("I"); //Inventaire
            case "a" , "A" -> System.out.println("A"); //Attaque distance
            case "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9"  -> System.out.println("Nombre"); //Objets
            case "e" , "E" -> System.exit(0);
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
        executor.scheduleAtFixedRate(() -> Affichage.getMap(map), 0, 100, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running && m.getPv()>0 && m.getEtage().equals(player.getEtage())) m.updateMonster();
        }, 0, m.getUpdate_rate_ms(), TimeUnit.MILLISECONDS);
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
}
