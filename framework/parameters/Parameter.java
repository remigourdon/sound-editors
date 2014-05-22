/**
 * Interface to represent any kind of parameter.
 */
public interface Parameter {
    /**
     * Get the current value of the Parameter object.
     * @return the value
     */
    public Object getValue();

    /**
     * Set a new value in the Parameter  object.
     * @param v the new value
     */
    public void setValue(Object v);

    public String toString();
}