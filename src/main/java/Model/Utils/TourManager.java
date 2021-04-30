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

/**
 * Classe qui gère ce qui se passe a chaque tour.
 * @author Quentin
 */
public class TourManager implements Serializable {
    private static boolean running = System.getProperty("os.name").equals("Linux");
    private static boolean inDialogue = false;
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static int Tour = 0;
    private static long Timer;
    private final BasicPlayer player;
    private Map map;

    /**
     * Prend un joueur en parametre.
     * @param player Joueur
     * @author Quentin
     */
    public TourManager(BasicPlayer player) {
        this.player = player;
        Timer = System.currentTimeMillis();
    }

    /**
     * Initialise la Map.
     * @author Quentin
     */
    public void setMap(){
        map = new Map(player);
    }

    /**
     * Methode appelé chaque tour et qui permet de réaliser l'action du joueur (et des mobs si TpT).
     * @param reader Reader
     * @author Quentin
     */
    public void playTour(BufferedReader reader){
        if(!inDialogue){
            Affichage.getMap();
            boolean mouvement = processInput(reader);
            processEtage();
            if(!running && mouvement){
                Tour++;
                for(AbstractMonster m : player.getEtage().getMonsters()){
                    m.updateMonster();
                    if(Tour % Math.ceil((float) m.getUpdate_rate_ms()/300)==0){
                        m.updateMonster();
                    }
                }
            }
        }
    }

    /**
     * Lis la premier char de la console comme une commande.
     * -Depuis le terminal pas besoin d'appuyer sur entré.
     * -Depuis un IDE la console est null donc il faut passé par un Reader et appuyer sur entré.
     * @param reader Que depuis un IDE
     * @return Si le joueur s'est deplacé ou a attaqué.
     * @author Quentin
     */
    private boolean processInput(BufferedReader reader) {
        char cmd = 0;
        if(System.console()!=null){
            char[] input = System.console().readPassword();
            if(input.length>0){
                cmd = input[0];
                
            }
        }
        else{
            String input = "";
            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(input.length()>0){
                cmd = input.charAt(0);
            }
        }
        switch (cmd){
            case 'z' , 'Z' -> {
                player.moveUp();
                return true;
            }
            case 'q' , 'Q' -> {
                player.moveLeft();
                return true;
            }
            case 's' , 'S' -> {
                player.moveDown();
                return true;
            }
            case 'd' , 'D' -> {
                player.moveRight();
                return true;
            }
            case 't' , 'T' -> TourParTour();
            case 'i' , 'I' -> player.getInventory().switchWeapons(); //Inventaire
            case 'p' , 'P' -> player.getInventory().switchPotions(); // Potions
            case 'a' , 'A' -> {
                player.getInventory().useCurrentWeapon(player); //Attaque distance
                return true;
            }
            case 'y' , 'Y' -> {
                player.getInventory().useCurrentPotion(player); //Utilise la potion courrante
                return true;
            }
            case 'w' , 'W' -> Start.sauvegarde();
            case '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9'  -> System.out.println("Nombre"); //Objets
            case 27 , 3 -> Start.end(); //TODO sa capte aussi les fleches du coup
            default -> processInput(reader);
        }
        return false;
    }

    /**
     * Action selon la case ou le joueur se deplace.
     * @author Quentin, JP
     */
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

    /**
     * Gere l'affichage et les messages periodiquement.
     * @author Quentin
     */
    public void schedule() {
        executor.scheduleAtFixedRate(() -> {
            if(running){
                Affichage.getMap();
            }
        }, 0, 300, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> {
            if(running && player.getEndurence()<100){
                player.updateEndurence(1);
            }
        }, 0, player.getClasse().getEndurenceRate(), TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(messages::pollFirst, 8, 8, TimeUnit.SECONDS);
    }

    /**
     * Crée des schedule pour un Monstre ce qui lui permet de se mettre a jour periodiquement.
     * @param m Monstre
     * @author Quentin
     */
    public static void addMonsterSchedule(AbstractMonster m){
        executor.scheduleAtFixedRate(() -> {
            if (running){
                if(m.getPv()>0 && m.getEtage().equals(Objects.requireNonNull(Start.getPlayer()).getEtage())) {
                    m.updateMonster();
                }
            }
        }, 10, m.getUpdate_rate_ms(), TimeUnit.MILLISECONDS);
    }

    /**
     * Change le mode de jeu de temps réel a Tour par Tour.
     * @author Quentin
     */
    public static void TourParTour(){
        TourManager.addMessage(running ? "Le jeu est en mode Tour par tour" : "Le jeu n'est plus en mode Tour par tour");
        Pause();
    }

    /**
     * Met en pause le jeu.
     * @author Quentin
     */
    public static void Pause(){
        running=!running;
    }

    /**
     * Change vers quoi va la capture des touches.
     * @author Quentin
     */
    public static void InDialogue(){
        Start.setConsoleMode(false);
        inDialogue=!inDialogue;
    }

    /**
     * Ajoute le message a la queue des messages du jeu.
     * @param message Message
     * @author Quentin
     */
    public static void addMessage(String message){
        if(messages.size()>8){
            messages.pollFirst();
        }
        messages.add(message);
    }

    /**
     * Renvoit la queue des messages.
     * @return Queue des messages
     * @author Quentin
     */
    public static ArrayDeque<String> getMessages(){
        return messages;
    }

    /**
     * Renvoit l'executor.
     * @return Executor
     * @author Quentin
     */
    public static ScheduledExecutorService getExecutor(){
        return executor;
    }

    /**
     * Renvoit le joueur.
     * @return Joueur
     * @author Quentin
     */
    public BasicPlayer getPlayer() {
        return player;
    }

    /**
     * Renvoit la map.
     * @return Map
     * @author Quentin
     */
    public Map getMap() {
        return map;
    }

    /**
     * Renvoit le temps passé depuis le debut de la partie sous le format hh:mm:ss.
     * @return Temps.
     * @author Quentin
     */
    public static String getTimer(){
        long durationInMillis = System.currentTimeMillis() - Timer;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
