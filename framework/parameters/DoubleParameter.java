package framework.parameters;

import framework.parameters.Parameter;

/**
 * Class representing any double Parameter.
 */
public class DoubleParameter extends Parameter {
    /**
     * Create an DoubleParameter object.
     * @param  n   the name of the parameter
     * @param  max the maximum value to accept
     * @param  min the minimum value to accept
     */
    public DoubleParameter(String n, Double max, Double min) {
        super(n);
        name = n;
        maxValue = max;
        minValue = min;
    }

    public Object parseValue(Object v) {
        // If the value is a text
        // Try to parse a Double from it
        if(v instanceof String) {
            try {
                return Double.parseDouble(v.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if(v instanceof Double) {
            return v;
        }
        return null;
    }

    private String      name;
    private Double      maxValue;
    private Double      minValue;
}