package framework;

import java.util.ArrayList;
import framework.editors.SoundEditor;

/**
 * Represents a sound entity.
 *
 * A sound is represented basically by a frequency and a duration.
 * The basic wave is synthetised using a generator object.
 * It can then be modified by adding different modifiers.
 * It is the core of the model in our MVC design pattern implementation.
 */
public class Sound extends Observable {
    /**
     * Creates a Sound object.
     * @param  g the generator to be used
     * @param  f the frequency of the signal
     * @param  d the duration of the sound
     */
    public Sound(Generator g, int f, double d) {
        generator   = g;
        frequency   = f;
        duration    = d;

        generateSignal();
    }

    /**
     * Generate the signal data from the basic wave and modifiers.
     */
    public void generateSignal() {
        byte[] signal = generator.generate(frequency, duration, SAMPLE_RATE);

        foreach(Modifier m : modifiers)
            signal = m.apply(signal);

        data = signal;
    }

    /**
     * Add a new Modifier to the list.
     * @param m the new Modifier to be added
     */
    public void addModifier(Modifier m) {

    }

    /**
     * Remove the specified Modifier from the list.
     * @param m the Modifier to be removed
     */
    public void removeModifier(Modifier m) {

    }

    /**
     * Instanciate a SoundEditor and hand back its reference.
     * @return the reference to the SoundEditor
     */
    public SoundEditor addEditor() {

    }

    private Generator           generator;
    private int                 frequency;  // Hertzs
    private double              duration;   // Milliseconds
    private byte[]              data;
    private ArrayList<Modifier> modifiers;

    // Constants
    private final int SAMPLE_RATE   = 44100;    // CD quality audio
    private final int MAX_16_BITS   = Short.MAX_VALUE;
}