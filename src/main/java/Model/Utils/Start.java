package Model.Utils;import Model.Entitys.Monsters.AbstractMonster;import Model.Entitys.Player.BasicPlayer;import Model.Entitys.Player.Classes.ClassFactory;import Model.Map.Etage;import Model.Map.Map;import java.io.*;import java.time.LocalDateTime;import java.time.format.DateTimeFormatter;import java.util.ArrayList;import java.util.Arrays;import java.util.Objects;import java.util.Random;/** * Classe de depart du jeu. * @author Quentin */public class Start {    private static TourManager tourManager;    /**     * Crée le TourManager et appele playTour.     * @author Quentin     */    public static void StartGame(){        Start.setConsoleMode(false);        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));        tourManager = getInfo(reader);        tourManager.schedule();        while (true) {            tourManager.playTour(reader);        }    }    /**     * Demande au joueur s'il veut charger une sauvegarde ou commencer une nouvelle partie.     * @param reader Lis la console     * @return Renvoit le TourManager     * @author Quentin     */    private static TourManager getInfo(BufferedReader reader){        Affichage.start();        try{            switch (reader.readLine()) {                case "1" -> {                    return init(reader);                }                case "2" -> {                    return load(reader);                }                case "3" -> System.exit(0);            }        } catch (IOException e) {            Start.setConsoleMode(false);            e.printStackTrace();        }        return getInfo(reader);    }    /**     * Crée le TourManager selon le Joueur et la Map configuré.     * @param reader Lis la console     * @return TourManager     * @author Quentin     */    private static TourManager init(BufferedReader reader){        System.out.print(Affichage.BLUE+"JOUEUR: "+Affichage.RESET);        String nom = "";        try {            nom = reader.readLine();        } catch (IOException e) {            Start.setConsoleMode(false);            e.printStackTrace();        }        System.out.print(Affichage.BRIGTH_PURPLE+"Numero seed: "+Affichage.RESET);        long seed_value;        try{            String value = "";            try {                value = reader.readLine();            } catch (IOException e) {                Start.setConsoleMode(false);                e.printStackTrace();            }            seed_value = Long.parseLong(value);        } catch (NumberFormatException e){            seed_value = new Random().nextLong();        }        Procedure.setSeed(seed_value);        return new TourManager(selectClass(reader,nom));    }    /**     * Selection le classe du joueur et l'initilise.     * @param reader Reader     * @param nom Nom du joueur     * @return Joueur     * @author Quentin     */    private static BasicPlayer selectClass(BufferedReader reader, String nom){        ClassFactory.Class[] values = ClassFactory.Class.values();        System.out.println(Affichage.YELLOW+"Quelle Classe voulez vous ?"+Affichage.RESET);        for (int i = 0; i < values.length; i++) {            System.out.println(Affichage.BRIGTH_YELLOW+(i+1)+". "+ values[i]+Affichage.RESET);        }        while(true){            try{                int value = Integer.parseInt(reader.readLine())-1;                if(value>0 && value< values.length){                    return ClassFactory.getNewPlayer(nom, values[value]);                }                else{                    System.out.println(Affichage.RED+"Classe non valide."+Affichage.RESET);                }            }            catch (IOException e){                Start.setConsoleMode(false);                e.printStackTrace();            }            catch (NumberFormatException e){                System.out.println(Affichage.RED+"Classe non valide."+Affichage.RESET);            }        }    }    /**     * Affiche les sauvegardes existantes et permet d'en charger une.     * @param reader Lis la console     * @return TourManager     * @author Quentin     */    private static TourManager load(BufferedReader reader){        File folder = new File("Saves");        if(!folder.exists() || Objects.requireNonNull(folder.listFiles()).length==0){            System.out.println(Affichage.RED+"Il n'y a aucune sauvegarde."+Affichage.RESET);            return init(reader);        }        System.out.println(Affichage.BLUE+"Veuillez choisir une sauvegarde:"+Affichage.RESET);        ArrayList<File> listFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));        System.out.print(Affichage.BRIGTH_BLUE);        for (int i = 0; i < listFiles.size(); i++) {            System.out.println(i+". "+listFiles.get(i).getName());        }        int index = 0;        try {            index = Integer.parseInt(reader.readLine());        } catch (IOException e) {            Start.setConsoleMode(false);            e.printStackTrace();        } catch (NumberFormatException e){            System.out.println(Affichage.RED+"Nom de sauvegarde invalide."+Affichage.RESET);            end();        }        TourManager tm = null;        try{            ObjectInputStream in = new ObjectInputStream(new FileInputStream(listFiles.get(index).getAbsolutePath()));            tm = (TourManager) in.readObject();            for(Etage e : tm.getMap().getEtages()){                for(AbstractMonster m : e.getMonsters()){                    TourManager.addMonsterSchedule(m);                }            }        } catch (IOException | ClassNotFoundException e) {            Start.setConsoleMode(false);            e.printStackTrace();        }        return tm;    }    /**     * Renvoit le joueur.     * @return Joueur     * @author Quentin     */    public static BasicPlayer getPlayer(){        return tourManager == null ? null : tourManager.getPlayer();    }    /**     * Renvoit la Map.     * @return Map     * @author Quentin     */    public static Map getMap() {        return tourManager == null ? null : tourManager.getMap();    }    /**     * Permet de passer la console en mode raw/cooked pour ne pas a avoir a appuyer sur entré pour la lire.     * @param raw bool     * @author Quentin     */    public static void setConsoleMode(boolean raw){        try{            //TODO faire l'equivalent sur windows si existe            if(System.getProperty("os.name").equals("Linux")){                Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", (raw ? "stty raw </dev/tty" : "stty cooked </dev/tty")}).waitFor();            }        }        catch (Exception e){            Start.setConsoleMode(false);            e.printStackTrace();        }    }    /**     * Sauvegarde le TourManager sous forme de bit dans un fichier.     * @author Quentin     */    public static void sauvegarde(){        try{            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");            new File("Saves/").mkdir();            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Saves/"+ Objects.requireNonNull(Start.getPlayer()).getNom()+"-"+dtf.format(LocalDateTime.now())));            out.writeObject(tourManager);            System.out.println(Affichage.RED+"Le jeu a été sauvegardé avec succés."+Affichage.RESET);            end();        } catch (IOException e) {            Start.setConsoleMode(false);            e.printStackTrace();        }    }    /**     * Quitter le programme et remet la console dans sa configuration de base.     * @author Quentin     */    public static void end(){        Start.setConsoleMode(false);        System.exit(1);    }}