package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Map.Map;

import java.io.Console;
import java.util.ArrayDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TourManager{
    private static boolean running = true;
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static BasicPlayer player;
    private final Map map;
    private static Etage etage;

    public TourManager(BasicPlayer player, Map map, Etage etage) {
        this.map = map;
        TourManager.player = player;
        TourManager.etage = etage;
        schedule();
        Affichage.getMap(map);
    }

    public void playTour() {
        Console console = System.console();
        if(console!=null){
            processInput(console.readPassword());
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

    private void processInput(char[] input){
        if(input.length>0){
            switch (input[0]) {
                case 'z' , 'Z' -> player.moveUp();
                case 'q' , 'Q' -> player.moveLeft();
                case 's' , 'S' -> player.moveDown();
                case 'd' , 'D' -> player.moveRight();
                case 't' , 'T' -> TourParTour();
                case 'i' , 'I' -> System.out.println("I"); //Inventaire
                case 'a' , 'A' -> System.out.println("A"); //Attaque distance
                case '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9'  -> System.out.println("Nombre"); //Objets
                case 'e' , 'E' , 3 -> Start.end();
            }
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
        //executor.scheduleAtFixedRate(() -> Affichage.getMap(map), 0, 200, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running && m.getPv()>0 && m.getEtage().equals(player.getEtage())){
                m.updateMonster();
                Affichage.getMap(Start.getMap());
            }
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
