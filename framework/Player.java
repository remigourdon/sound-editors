package framework;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import framework.Sound;

/**
 * Manage a list of Sound objects.
 *
 * This class provides methods to play, pause and mix different sounds.
 */
public class Player {
    public Player() {
        final AudioFormat af = new AudioFormat(
            Generator.SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, true);

        try {
            line = AudioSystem.getSourceDataLine(af);
            line.open();
        } catch(Exception e) {
            e.printStackTrace();
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
     * Mix the Sound objects contains in the list.
     * @param  l the list of Sound objects to be mixed
     * @return   the byte array containing the mix
     */
    public byte[] mix(ArrayList<Sound> l) {
        int maxDuration = 0;

        for(Sound s : l) {
            if(s.getDuration() > maxDuration)
                maxDuration = s.getDuration();
        }

        byte[] result = new byte[maxDuration * SAMPLE_RATE];

        for(int i = 0; i < result.length; i++) {
            ArrayList<Sound> done = new ArrayList<Sound>();
            for(Sound s : l) {
                result[i] = (byte)(s.getData()[i] + result[i]);
                if(i + 1 >= s.getData().length)
                    done.add(s);
            }
            l.removeAll(done);
        }

        return result;
    }

    /**
     * Play all the Sound objects which are not muted.
     */
    public void play() {
        ArrayList<Sound> selection = new ArrayList<Sound>();

        for(Map.Entry<Sound, Boolean> entry : playlist.entrySet()) {
            if(entry.getValue())
                selection.add(entry.getKey());
        }

        byte[] theMix = mix(selection);
        line.start();
        line.write(theMix, 0, theMix.length);
        line.stop();
        line.drain();
    }

    private SourceDataLine              line;
    private HashMap<Sound, Boolean>     playlist;

    // Constants
    private final int BITS_PER_SAMPLE   = 16;
}