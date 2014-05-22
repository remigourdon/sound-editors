package framework.parameters;

import java.util.Observable;

/**
 * Abstract class to represent any kind of parameter.
 */
public class Parameter extends Observable {
    /**
     * Create a Parameter object.
     * @param  n the name of the parameter
     */
    public Parameter(String n) {
        name = n;
    }

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
        value = v;
        setChanged();
        notifyObservers(v);
    }

    public String toString() {
        return name;
    }

    private String  name;
    private Object  value;
}