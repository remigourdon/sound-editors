package framework;

import java.util.ArrayList;
import java.util.Observable;
import framework.generators.Generator;
import framework.editors.SoundEditor;

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
     * @param  a the amplitude of the sound
     */
    public Sound(Generator g, Double f, Double d, Double a) {
        generator   = g;
        frequency   = f;
        duration    = d;
        amplitude   = a;

        generateSignal();
    }

    /**
     * Generate the signal data from the basic wave.
     */
    public void generateSignal() {
        Double[] signal = generator.generate(frequency, duration, amplitude);

        data = signal;

        setChanged();
        notifyObservers(true);  // Signal to observers that the data has changed
    }

    /**
     * Attach a new SoundEditor to the Sound object.
     * @return the SoundEditor newly attached
     */
    public SoundEditor attachEditor() {
        SoundEditor editor = new SoundEditor(this);
        addObserver(editor);
        return editor;
    }


    /**
     * Get the frequency of the Sound.
     * @return the frequency in hertz
     */
    public Double getFrequency() {
        return frequency;
    }

    /**
     * Set the frequency of the Sound.
     * @param f the new frequency in hertz
     */
    public void setFrequency(Double f) {
        if(f >= 0) {
            frequency = f;
            generateSignal();
        } else {
            setChanged();
            notifyObservers(false);
        }
    }

    /**
     * Get the duration of the Sound.
     * @return the duration in seconds
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * Set the duration of the Sound.
     * @param d the new duration in seconds
     */
    public void setDuration(Double d) {
        if(d >= 0) {
            duration = d;
            generateSignal();
        } else {
            setChanged();
            notifyObservers(false);
        }
    }

    /**
     * Get the amplitude of the Sound.
     * @return the amplitude
     */
    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double a) {
        if(a >= 0) {
            amplitude = a;
            generateSignal();
        } else {
            setChanged();
            notifyObservers(false);
        }
    }

    /**
     * Get the data of the Sound.
     * @return the data as an array of Doubles
     */
    public Double[] getData() {
        return data;
    }

    private Generator           generator;
    private Double              frequency;  // Hertzs
    private Double              duration;   // Seconds
    private Double              amplitude;
    private Double[]            data;
}