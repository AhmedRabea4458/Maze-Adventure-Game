import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {


    private static boolean muted = false;


    public static void play(Sound sound) {
        if (muted) return;
        play(sound.path);
    }

    public static void toggleMute() {
        muted = !muted;
    }

    public static boolean isMuted() {
        return muted;
    }


    private static void play(String path) {
        try {
            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            SoundManager.class.getResource(path)
                    );

            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




