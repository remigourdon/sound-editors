package framework;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import framework.generators.Generator;
import framework.editors.SoundEditor;
import framework.views.View;
import framework.modifiers.Modifier;
import framework.parameters.DoubleParameter;

/**
 * Represents a sound entity.
 *
 * A sound is represented basically by a frequency and a duration.
 * The basic wave is synthetised using a generator object.
 * It is the core of the model in our MVC design pattern implementation.
 */
public class Sound extends Observable implements Observer {
    /**
     * Creates a Sound object.
     * @param  g the generator to be used
     * @param  f the frequency of the signal
     * @param  d the duration of the sound
     * @param  a the amplitude of the sound
     */
    public Sound(Generator g, Double f, Double d, Double a) {
        generator   = g;

        // Creates parameters
        frequency   = new DoubleParameter("Frequency", f, 0., 20000.);
        duration    = new DoubleParameter("Duration", d, 0., 60.);
        amplitude   = new DoubleParameter("Amplitude", a, 0., 100.);

        modifiers = new ArrayList<Modifier>();

        generateSignal();
    }

    public void update(Observable o, Object arg) {
        if(o instanceof Modifier) {
            generateSignal();
        }
    }

    /**
     * Generate the signal data from the basic wave.
     */
    public void generateSignal() {
        Double[] signal = generator.generate(frequency.getValue(), duration.getValue(), amplitude.getValue());

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
     * Get the value of the specified DoubleParameter.
     * @param  p the DoubleParameter
     * @return   the current value
     */
    public Double getParameterValue(DoubleParameter p) {
        return p.getValue();
    }

    /**
     * Set the value of the specified DoubleParameter.
     * @param p the DoubleParameter
     * @param v the new value
     */
    public void setParameterValue(DoubleParameter p, Double v) {
        // If the value has been updated
        if(p.setValue(v)) {
            generateSignal();
        } else {
            // Notify observers but informs them that models didn't change
            // This is convenient to reload good values in editors
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
    private DoubleParameter     frequency;  // Hertzs
    private DoubleParameter     duration;   // Seconds
    private DoubleParameter     amplitude;
    private Double[]            data;
    private ArrayList<Modifier> modifiers;
}