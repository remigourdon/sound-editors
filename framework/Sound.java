package framework;

import java.util.ArrayList;

/**
 * Represents a sound entity.
 *
 * A sound is represented basically by a frequency and a duration.
 * The basic wave is synthetised using a generator object.
 * It is the core of the model in our MVC design pattern implementation.
 */
public class Sound extends Observable {
    /**
     * Creates a Sound object.
     * @param  g the generator to be used
     * @param  f the frequency of the signal
     * @param  d the duration of the sound
     */
    public Sound(Generator g, int f, int d) {
        generator   = g;
        frequency   = f;
        duration    = d;
        data        = null;

        generateSignal();
    }

    /**
     * Generate the signal data from the basic wave.
     */
    public void generateSignal() {
        byte[] signal = generator.generate(frequency, duration, SAMPLE_RATE);

        data = signal;

        setChanged();
        notifyObservers(data);
    }

    /**
     * Get the duration of the Sound.
     * @return the duration in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Get the data of the Sound.
     * @return the data as an array of bytes
     */
    public byte[] getData() {
        return data;
    }

    private Generator           generator;
    private int                 frequency;  // Hertzs
    private int                 duration;   // Seconds
    private byte[]              data;

    // Constants
    private final int SAMPLE_RATE   = 44100;    // CD quality audio
}