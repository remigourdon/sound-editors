package framework.modifiers;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import framework.Sound;
import framework.parameters.Parameter;

/**
 * Abstract class to represent all kind of filters and effects.
 */
public abstract class Modifier extends Observable implements Observer {
    /**
     * Create a Modifier object.
     */
    public Modifier(Sound s) {
        addObserver(s);
    }

    public void update(Observable o, Object arg) {
        // If a parameter has been modified
        // Inform the Sound object that the Modifier changed
        if(o instanceof Parameter && arg == true) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Apply the transformations to the array and return the new data.
     * @param  data the current data
     * @return      the new data
     */
    public abstract Double[] apply(Double[] data);

    /**
     * Get all the parameters of the Modifier object.
     * @return the array of Parameter
     */
    public abstract Parameter[] getParameters();

    /**
     * Get the name of the Modifier (clearer than toString() for this purpose).
     * @return the name of the Modifier
     */
    public String getName() {
        return toString();
    }

    public abstract String toString();
}