import javax.swing.*;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;

/**
 * Represents a class that plays sounds.
 */
public class Sound {
    public static void playSound(final String fileName) {

        try {
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Clip clip = AudioSystem.getClip();
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                                    Main.class.getResourceAsStream("/Sounds/" + fileName)));
                            clip.open(inputStream);
                            clip.start(); 
                        } catch (Exception e) {}
                    }
                });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}