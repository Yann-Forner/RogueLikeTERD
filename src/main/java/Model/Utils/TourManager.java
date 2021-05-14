package Model.Utils;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.Player;
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
    private static boolean running = System.getProperty("os.name").equals("Linux") && System.console()!=null;
    private static boolean inDialogue = false;
    private static final ArrayDeque<String> messages = new ArrayDeque<>();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static final int refreshRate = 300;
    private static int Tour = 0;
    private static long Timer;
    private final Player player;
    private Map map;
    private static int nbrKill = 0;
    private static int nbrKillBoss = 0;
    private static int nbrObjetsTotal = 0;

    /**
     * Prend un joueur en parametre.
     * @param player Joueur
     * @author Quentin
     */
    public TourManager(Player player) {
        this.player = player;
        Timer = System.currentTimeMillis();
        Sound.playAudio(Sound.Sons.MUSIQUE,1);
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
            Affichage.getMap(true);
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
            if(input != null && input.length>0){
                cmd = input[input.length-1];
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
                player.moveDirection(Player.Direction.HAUT);
                return true;
            }
            case 'q' , 'Q' -> {
                player.moveDirection(Player.Direction.GAUCHE);
                return true;
            }
            case 's' , 'S' -> {
                player.moveDirection(Player.Direction.BAS);
                return true;
            }
            case 'd' , 'D' -> {
                player.moveDirection(Player.Direction.DROITE);
                return true;
            }
            case 't' , 'T' -> TourParTour();
            case 'i' , 'I' -> player.getInventory().switchWeapons(); //Changement armes
            case 'o' , 'O' -> player.getInventory().switchPotions(); //Changement Potions
            case 'l' , 'L' -> player.getInventory().dropItem(player, player.getInventory().getCurrentWeapon()); // Drop de l'arme courante
            case 'm' , 'M' -> player.getInventory().dropItem(player, player.getInventory().getCurrentPotion()); // Drop de la potion courante
            case 'a' , 'A' -> {
                player.getInventory().useCurrentWeapon(player); //Attaque à distance
                return true;
            }
            case 'p' , 'P' -> {
                player.getInventory().useCurrentPotion(player); //Utilise la potion courrante
                return true;
            }
            case 'w' , 'W' -> Start.sauvegarde();
            case 3 , 27 -> Start.end();
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
                if(map.getMonterDescendre()){
                    map.setMonterDescendre(true);
                }
                else{
                    map.UP();
                    Sound.playAudio(Sound.Sons.TP,0);
                }
                break;
            case DOWN :
                if(map.getMonterDescendre()){
                    map.setMonterDescendre(true);
                }
                else{
                    map.DOWN();
                    Sound.playAudio(Sound.Sons.TP,0);
                }
                break;
            case TRAP_ROOM :
                map.TRAP_ROOM();
                Sound.playAudio(Sound.Sons.TP,0);
                break;
            default:
                map.setMonterDescendre(false);
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
                Affichage.getMap(false);
            }
        }, 100, refreshRate, TimeUnit.MILLISECONDS);
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
            if(running){
                if(m.getPv()>0 && m.getEtage().equals(Objects.requireNonNull(Start.getPlayer()).getEtage())) {
                    m.updateMonster();
                    Affichage.getMap(false);
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
        if(messages.size()>9){
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
    public Player getPlayer() {
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

    /**
     * Renvoit le tour du jeu.
     * @return int
     * @author Quentin
     */
    public int getTour(){
        return Tour;
    }

    /**
     * Renvoit la frequence de reafrchissement de la map
     * @return Temps en ms
     * @author Quentin
     */
    public static int getRefreshRate(){
        return refreshRate;
    }

    /**
     * Ajoute un monstre tué au nombre de tués.
     * @author Quentin
     */
    public static void addKill(){
        nbrKill++;
    }

    /**
     * Renvoit le nombre de monstres tués au total.
     * @return nbr
     * @author Quentin
     */
    public static int getNbrKill(){
        return nbrKill;
    }

    /**
     * Ajoute un boss tué au nombre de tués.
     * @author Quentin
     */
    public static void addKillBoss(){
        nbrKillBoss++;
        nbrKill++;
    }

    /**
     * Renvoit le nombre de boss tués au total.
     * @return nbr
     * @author Quentin
     */
    public static int getNbrKillBoss(){
        return nbrKillBoss;
    }

    /**
     * Ajoute un objets recuperé au nombre total des objets recuperés.
     * @author Quentin
     */
    public static void addNbrObjetsTotal(){
        nbrObjetsTotal++;
    }

    /**
     * Renvoit le nombre d'objets total d'objets recuperées durant la partie.
     * @return nbr
     * @author Quentin
     */
    public static int getNbrObjetsTotal(){
        return nbrObjetsTotal;
    }
}
