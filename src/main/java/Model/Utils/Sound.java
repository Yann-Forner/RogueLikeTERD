package Model.Utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public enum Sons{
        TP("teleportation.wav"),
        MUSIQUE("musique.wav"),
        COLLISION("collision.wav"),
        LEVELUP("levelup.wav");

        private final String path;

        Sons(String path){
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static void playAudio(Sons son, int nbrLoop){
        try{
            Clip clip = AudioSystem.getClip();
            URL fichier = ClassLoader.getSystemResource(son.getPath());
            AudioInputStream ais = AudioSystem.getAudioInputStream(fichier);
            clip.open(ais);
            clip.loop(nbrLoop>=0 ? nbrLoop : Clip.LOOP_CONTINUOUSLY);
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException ignored){ }
    }
}
