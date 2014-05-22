package framework.parameters;

import java.util.Observable;

import framework.editors.ParameterEditor;

/**
 * Abstract class to represent any kind of parameter.
 */
public abstract class Parameter extends Observable {
    /**
     * Create a Parameter object.
     * @param  n the name of the parameter
     */
    public Parameter(String n) {
        name = n;
    }

    /**
     * Test the value to see if it fits the requirements.
     * @param  text the text to be tested
     * @return      True if valid, False otherwise
     */
    public abstract boolean isValid(Object v);

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
    public Object getValue() {
        return value;
    }

    /**
     * Set a new value in the Parameter object.
     * @param v the new value
     */
    public void setValue(Object v) {
        // If the value is correct
        // Informs the modifier that the parameter changed
        if(isValid(v)) {
            value = v;
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
    private Object  value;
}