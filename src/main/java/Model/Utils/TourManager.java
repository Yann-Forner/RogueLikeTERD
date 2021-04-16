package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.BasicPlayer;
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
        TourManager.player = player;
        this.map = map;
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
        Affichage.getMap(map);
    }

    private void processInput(String input){
        switch (input) {
            case "z" , "Z" , "\u001B[A" -> {
                if(running) {
                    player.moveUp();
                }
                else{
                    addMessage(Affichage.RED+Affichage.BOLD+"[PAUSE]"+Affichage.RESET);
                }
            }
            case "q" , "Q" , "\u001B[D" -> {
                if(running) {
                    player.moveLeft();
                }
                else{
                    addMessage(Affichage.RED+Affichage.BOLD+"[PAUSE]"+Affichage.RESET);
                }
            }
            case "s" , "S" , "\u001B[B" -> {
                if(running) {
                    player.moveDown();
                }
                else{
                    addMessage(Affichage.RED+Affichage.BOLD+"[PAUSE]"+Affichage.RESET);
                }
            }
            case "d" , "D" , "\u001B[C" -> {
                if(running) {
                    player.moveRight();
                }
                else{
                    addMessage(Affichage.RED+Affichage.BOLD+"[PAUSE]"+Affichage.RESET);
                }
            }
            case "p" , "P" -> pause();
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

    public static void pause(){
        TourManager.addMessage(running ? "Le jeu est en pause" : "Le jeu n'est plus en pause");
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
