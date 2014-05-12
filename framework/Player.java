package framework;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Observable;
import framework.Sound;
import framework.generators.Generator;

/**
 * Manage a list of Sound objects.
 *
 * This class provides methods to play, pause and mix different sounds.
 */
public class Player extends Observable {
    public Player() {
        try {
            // 44,100 samples per second, 16-bit audio, mono, signed PCM, little Endian
            AudioFormat format = new AudioFormat(
                (float) Generator.SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, false);

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);

            // The internal buffer is a fraction of the actual buffer size
            // It gets divided because we can't expect the buffered data to line up
            // exactly with when the sound card decides to push out its samples.
            buffer = new byte[SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE/3];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        playlist = new HashMap<Sound, Boolean>();
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

    /**
     * Play a Sound object.
     * @param s the Sound object to be played
     */
    public void play(Sound s) {
        Double[] data = s.getData();

        line.start();

        for(int i = 0; i < s.getDuration() * Generator.SAMPLE_RATE; i++) {
            // Clip
            if (data[i] < -1.0) data[i] = -1.0;
            if (data[i] > +1.0) data[i] = +1.0;

            short sh = (short) (MAX_16_BIT * data[i]);

            buffer[bufferSize++] = (byte) sh;
            buffer[bufferSize++] = (byte) (sh >> 8);   // Little Endian

            // Send to sound card if buffer is full
            if (bufferSize >= buffer.length) {
                line.write(buffer, 0, buffer.length);
                bufferSize = 0;
            }
        }

        line.drain();
        line.stop();

        bufferSize = 0;
    }

    /**
     * Play all the Sound objects which are not muted.
     */
    public void playAll() {
        ArrayList<Sound> selection = getPlayableSounds();

        line.start();

        // Find the maximum duration
        Double maxDuration = new Double(0);
        for(Sound s : selection) {
            if(s.getDuration() > maxDuration)
                maxDuration = s.getDuration();
        }

        for(int i = 0; i < (int) (maxDuration * Generator.SAMPLE_RATE); i++) {
            short sh = 0;
            for(Sound s : selection) {
                if(i < (int) (s.getDuration() * Generator.SAMPLE_RATE)) {
                    Double[] data = s.getData();

                    // Clip
                    if (data[i] < -1.0) data[i] = -1.0;
                    if (data[i] > +1.0) data[i] = +1.0;

                    sh = (short) ((MAX_16_BIT * data[i]) + sh);
                }
            }
            buffer[bufferSize++] = (byte) sh;
            buffer[bufferSize++] = (byte) (sh >> 8);   // Little Endian

            // Send to sound card if buffer is full
            if (bufferSize >= buffer.length) {
                line.write(buffer, 0, buffer.length);
                bufferSize = 0;
            }
        }

        line.drain();
        line.stop();

        bufferSize = 0;
    }

    /**
     * Get all the Sound objects that are not muted.
     * @return the list of playable Sound objects
     */
    public ArrayList<Sound> getPlayableSounds() {
        ArrayList<Sound> selection = new ArrayList<Sound>();
        for(Map.Entry<Sound, Boolean> entry : playlist.entrySet()) {
            if(entry.getValue())
                selection.add(entry.getKey());
        }
        return selection;
    }

    /**
     * Get all the Sound objects in the Player.
     * @return the array of all Sound objects
     */
    public ArrayList<Sound> getAllSounds() {
        return new ArrayList(playlist.keySet());
    }

    private SourceDataLine              line;
    private HashMap<Sound, Boolean>     playlist;
    private byte[]                      buffer; // Internal buffer
    private int                         bufferSize = 0; // Current number of samples in buffer

    // Constants
    private final int       BYTES_PER_SAMPLE    = 2;
    private final int       BITS_PER_SAMPLE     = 16;
    private final Double    MAX_16_BIT          = new Double(Short.MAX_VALUE);
    private final int       SAMPLE_BUFFER_SIZE  = 4096;
}