package sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio {
    private File sound;
    private Clip music;

    public Audio() {
        sound = new File("baggage room.wav");

        try {
            music = AudioSystem.getClip();
        } catch (LineUnavailableException lu) {
            System.err.println("LineUnavailableException: " + lu.getMessage());
        }
    }


    public void play() {
        if (!music.isRunning()) {
            try {
                music.open(AudioSystem.getAudioInputStream(sound));
                music.loop(music.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException lu) {
                System.err.println("LineUnavailableException: " + lu.getMessage());
            } catch (UnsupportedAudioFileException uaf) {
                System.err.println("UnsupportedAudioFileException: " + uaf.getMessage());
            } catch (IOException i) {
                System.err.println("IOException: " + i.getMessage());
            }
        }
    }
}
