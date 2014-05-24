package framework;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import framework.generators.Generator;
import framework.editors.SoundEditor;
import framework.views.View;
import framework.modifiers.Modifier;
import framework.parameters.Parameter;
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
        frequency   = new DoubleParameter(this, "Frequency", f, 0., 20000.);
        duration    = new DoubleParameter(this, "Duration", d, 0., 60.);
        amplitude   = new DoubleParameter(this, "Amplitude", a, 0., 100.);

        modifiers = new ArrayList<Modifier>();

        generateSignal();
    }

    public void update(Observable o, Object arg) {
        if(o instanceof Modifier || (o instanceof Parameter && arg == true)) {
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
        return new SoundEditor(this);
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
     * Get a Parameter object according to its name.
     * @param  s the required name
     * @return   the Parameter if it has been found, null otherwise
     */
    public Parameter getParameterByName(String s) {
        for(Parameter p : getParameters()) {
            if(p.getName() == s)
                return p;
        }
        return null;
    }

    /**
     * Returns an array containing all the Parameter objects of the Sound.
     * @return the Parameter array
     */
    public Parameter[] getParameters() {
        return new Parameter[]{frequency, duration, amplitude};
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