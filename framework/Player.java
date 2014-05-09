import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import framework.Sound;

/**
 * Handles the playing of the sounds inside the list.
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

        list = new ArrayList<Sound>();
    }

    private final SourceDataLine    line;
    private ArrayList<Sound>        list;

    // Constants
    private final int SAMPLE_RATE       = 44100;    // CD quality audio
    private final int MAX_16_BITS       = Short.MAX_VALUE;
    private final int BITS_PER_SAMPLE   = 16;
}