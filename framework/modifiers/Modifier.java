package framework.modifiers;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import framework.Sound;
import framework.parameters.Parameter;
import framework.editors.ModifierEditor;

/**
 * Abstract class to represent all kind of filters and effects.
 */
public abstract class Modifier extends Observable implements Observer {
    /**
     * Attach a Sound object to the Modifier.
     * @param  s the new Sound object to be attached
     */
    public void attachSound(Sound s) {
        addObserver(s);
    }

    public void update(Observable o, Object arg) {
        // If a parameter has been modified
        // Inform the Sound object that the Modifier changed
        if(o instanceof Parameter && (boolean) arg == true) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Attach a new ModifierEditor to the Modifier object.
     * @return the ModifierEditor newly attached
     */
    public ModifierEditor attachEditor() {
        return new ModifierEditor(this);
    }

    /**
     * Apply the transformations to the array and return the new data.
     * @param  data the current data
     * @param  f    the frequency of the Sound
     * @param  d    the duration of the Sound
     * @return      the new data
     */
    public abstract Double[] apply(Double[] data, Double f, Double d);

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