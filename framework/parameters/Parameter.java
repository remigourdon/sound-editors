package framework.parameters;

import java.util.Observable;
import java.util.Observer;

import framework.editors.ParameterEditor;

/**
 * Generic abstract class to represent any kind of parameter.
 */
public abstract class Parameter<T> extends Observable {
    /**
     * Create a Parameter object.
     * @param  o the observer of the Parameter
     * @param  n the name of the Parameter
     * @param  v the initial value of the Parameter
     */
    public Parameter(Observer o, String n, T v) {
        addObserver(o);
        name    = n;
        value   = v;
    }

    /**
     * Parse the value to see if it fits the requirements.
     * @param  v the value to be tested
     * @return   the value or null if it doesn't fit
     */
    public abstract T parseValue(T v);

    /**
     * Parse the value from a String and see if it fits the requirements.
     * @param  s the String to parse
     * @return   the value or null if it doesn't fit
     */
    public abstract T parseValue(String s);

    /**
     * Attach a new ParameterEditor to the Parameter object.
     * @return the ParameterEditor newly attached
     */
    public ParameterEditor attachEditor() {
        ParameterEditor editor = new ParameterEditor(this);
        addObserver(editor);
        return editor;
    }

    /**
     * Get the current value of the Parameter object.
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Set a new value in the Parameter object and notify observers.
     *
     * If the value has been updated, true is sent, false otherwise.
     *
     * @param  v the value possibly valid
     */
    public void setValue(T v) {
        // If the value is correct
        // Informs the modifier that the parameter changed
        if(parseValue(v) != null) {
            value = parseValue(v);
            setChanged();
            notifyObservers(true);
        }
        setChanged();
        notifyObservers(false);
    }

    /**
     * Set a new value in the Parameter object and notify observers.
     *
     * If the value has been updated, true is sent, false otherwise.
     *
     * @param  s the string possibly containing a new value
     */
    public void setValue(String s) {
        if(parseValue(s) != null) {
            value = parseValue(s);
            setChanged();
            notifyObservers(true);
        }
        setChanged();
        notifyObservers(false);
    }

    /**
     * Get the name of the Parameter (clearer than toString() for this purpose).
     * @return the name of the Parameter
     */
    public String getName() {
        return toString();
    }

    public String toString() {
        return name;
    }

    private String  name;
    private T       value;
}