package framework.parameters;

import framework.parameters.Parameter;

/**
 * Class that represents a Parameter of Double type.
 */
public class DoubleParameter extends Parameter<Double> {
    /**
     * Create a DoubleParameter object.
     * @param  n   the name of the parameter
     * @param  v   the initial value of the parameter
     * @param  min the minimal value of the parameter
     * @param  max the maximal value of the parameter
     */
    public DoubleParameter(String n, Double v, Double min, Double max) {
        super(n, v);
        minValue = min;
        maxValue = max;
    }

    public Double parseValue(Double v) {
        if(v <= maxValue && v >= minValue)
            return v;
        return null;
    }

    public Double parseValue(String s) {
        try {
            return parseValue(Double.parseDouble(s));
        } catch(NullPointerException|NumberFormatException e) {
            return null;
        }
    }

    private Double maxValue;
    private Double minValue;
}