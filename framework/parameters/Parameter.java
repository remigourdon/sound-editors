package framework.parameters;

import java.util.Observable;

import framework.editors.ParameterEditor;

/**
 * Generic abstract class to represent any kind of parameter.
 */
public abstract class Parameter<T> extends Observable {
    /**
     * Create a Parameter object.
     * @param  n the name of the parameter
     */
    public Parameter(String n, T v) {
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
     * Set a new value in the Parameter object.
     * @param v the new value
     */
    public void setValue(T v) {
        // If the value is correct
        // Informs the modifier that the parameter changed
        if(parseValue(v) != null) {
            value = parseValue(v);
            setChanged();
            notifyObservers();
        }
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