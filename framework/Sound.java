package framework;

import java.util.ArrayList;
import java.util.Observable;
import framework.generators.Generator;

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
    public Sound(Generator g, double f, double d) {
        generator   = g;
        frequency   = f;
        duration    = d;

        generateSignal();
    }

    /**
     * Generate the signal data from the basic wave.
     */
    public void generateSignal() {
        double[] signal = generator.generate(frequency, duration, amplitude);

        data = signal;

        setChanged();
        notifyObservers(data);
    }

    /**
     * Get the duration of the Sound.
     * @return the duration in seconds
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Get the data of the Sound.
     * @return the data as an array of doubles
     */
    public double[] getData() {
        return data;
    }

    private Generator           generator;
    private double              frequency;  // Hertzs
    private double              duration;   // Seconds
    private double              amplitude;
    private double[]            data;
}