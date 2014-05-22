package framework;

import java.util.ArrayList;
import java.util.Observable;

import framework.generators.Generator;
import framework.editors.SoundEditor;
import framework.views.View;
import framework.modifiers.Modifier;

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

        modifiers = new ArrayList<Modifier>();

        generateSignal();
    }

    /**
     * Generate the signal data from the basic wave.
     */
    public void generateSignal() {
        Double[] signal = generator.generate(frequency, duration, amplitude);

        data = signal;

        applyModifiers(modifiers);

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
     * Attach a new View to the Sound object.
     * @param v the View to be attached
     */
    public void attachView(View v) {
        addObserver(v);
    }

    /**
     * Add a new Modifier to the Sound object.
     * @param m the Modifier to be added
     */
    public void addModfier(Modifier m) {
        modifiers.add(m);
    }

    /**
     * Apply a list of modifiers to the Sound object.
     * @param ms the list of modifiers to be applied
     */
    public void applyModifiers(ArrayList<Modifier> ms) {
        Double[] newData = data;
        for(Modifier m : ms) {
            newData = m.apply(newData);
        }
        data = newData;
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
        if(f >= 0 && f != frequency) {
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
        if(d >= 0 && d != duration) {
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

    /**
     * Set the amplitude of the Sound.
     * @param a the new amplitude
     */
    public void setAmplitude(Double a) {
        if(a >= 0 && a != amplitude) {
            amplitude = a;
            generateSignal();
        } else {
            setChanged();
            notifyObservers(false);
        }
    }

    /**
     * Get the generator of the Sound.
     * @return the generator
     */
    public Generator getGenerator() {
        return generator;
    }

    /**
     * Set the generator of the Sound
     * @param g the new generator
     */
    public void setGenerator(Generator g) {
        generator = g;
        generateSignal();
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
    private ArrayList<Modifier> modifiers;
}