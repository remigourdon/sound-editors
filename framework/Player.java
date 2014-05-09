import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.Map;
import framework.Sound;

/**
 * Manage a list of Sound objects.
 *
 * This class provides methods to play, pause and mix different sounds.
 */
public class Player {
    public Player() {
        final AudioFormat af = new AudioFormat(
            SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, true);

        try {
            line = AudioSystem.getSourceDataLine(af);
            line.open();
        } catch(Exception e) {
            e.printStackTrace();
        }

        playlist = new Map<Sound, boolean>();
    }

    /**
     * Add the specified Sound to the playlist.
     * @param s the sound to be added
     */
    public void addSound(Sound s) {
        playlist.put(s, true);
    }

    /**
     * Remove the specified Sound from the playlist.
     * @param s the Sound to be removed
     */
    public void removeSound(Sound s) {
        playlist.remove(s);
    }

    /**
     * Toggle a Sound inside the playlist.
     *
     * If its value is true, it is added to the mix.
     * If its value is false, it is muted.
     * @param s the Sound to be toggled
     */
    public void toggleSound(Sound s) {
        if(playlist.get(s) != null)
            playlist.put(s, !playlist.get(s));
    }

    private final SourceDataLine    line;
    private Map<Sound, boolean>     playlist;

    // Constants
    private final int SAMPLE_RATE       = 44100;    // CD quality audio
    private final int MAX_16_BITS       = Short.MAX_VALUE;
    private final int BITS_PER_SAMPLE   = 16;
}