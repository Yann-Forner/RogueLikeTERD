package Model.Utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Classe qui premet de lire des sons.
 * Tous les fichiers viennent de Zapsplat.com
 * @author Quentin
 */
public class Sound {

    /**
     * Enum des Fichiers.
     * @author Quentin
     */
    public enum Sons{
        TP("teleportation.wav"),
        MUSIQUE("musique.wav"),
        COLLISION("collision.wav"),
        MANGER("manger.wav"),
        DEGATSJOUEUR("DegatsJoueur1.wav","DegatsJoueur2.wav","DegatsJoueur3.wav"),
        MORT("mort.wav"),
        LEVELUP("levelup.wav"),
        EPEEDAMAGE("Attaque epee 1.wav","Attaque epee 2.wav","Attaque epee 3.wav"),
        BOWDAMAGE("arc1.wav","arc2_1.wav","arc3_1.wav"),
        WANDDAMAGE("wand1.wav","wand2.wav","wand3.wav");

        private final String[] paths;

        Sons(String... paths){
            this.paths = paths;
        }

        public String getPath() {
            return paths[Procedure.getRandomInt(paths.length - 1,0)];
        }
    }

    /**
     * Lit des sons nbrLoop+1 fois si negatif lit a l'infini.
     * @param son Enum
     * @param nbrLoop le nombre de boucle du son
     * @author Quentin
     */
    public static void playAudio(Sons son, int nbrLoop){
        try {
            Clip clip = AudioSystem.getClip();
            URL fichier = ClassLoader.getSystemResource(son.getPath());
            if(fichier != null){
                AudioInputStream ais = AudioSystem.getAudioInputStream(fichier);
                clip.open(ais);
                clip.loop(nbrLoop>=0 ? nbrLoop : Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch (IllegalArgumentException | LineUnavailableException | UnsupportedAudioFileException | IOException ignored) {}
    }
}
