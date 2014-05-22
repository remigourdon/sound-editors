package framework.parameters;

import java.util.Observable;

/**
 * Generic class to represent any kind of parameter.
 */
public class Parameter<V> extends Observable {
    public Parameter<V>(String n) {
        name = n;
    }

    /**
     * Get the current value of the Parameter object.
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Set a new value in the Parameter  object.
     * @param v the new value
     */
    public void setValue(V v) {
        value = v;
    }

    public String toString() {
        return name;
    }

    private String  name;
    private V       value;
}