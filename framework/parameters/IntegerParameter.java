package framework.parameters;

import framework.parameters.Parameter;

/**
 * Class representing any integer Parameter.
 */
public class IntegerParameter extends Parameter {
    /**
     * Create an IntegerParameter object.
     * @param  n   the name of the parameter
     * @param  max the maximum value to accept
     * @param  min the minimum value to accept
     */
    public IntegerParameter(String n, Integer max, Integer min) {
        super(n);
        name = n;
        maxValue = max;
        minValue = min;
    }

    public Object parseValue(Object v) {
        // If the value is a text
        // Try to parse an Int from it
        if(v instanceof String) {
            try {
                return Integer.parseInt(v.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if(v instanceof Integer) {
            return v;
        }
        return null;
    }

    private String      name;
    private Integer     maxValue;
    private Integer     minValue;
}