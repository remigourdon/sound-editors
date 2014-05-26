package framework.modifiers;

import framework.modifiers.Modifier;
import framework.parameters.Parameter;
import framework.parameters.DoubleParameter;

/**
 * Implementation of a low-pass filter.
 */
public class LowPassFilter extends Modifier {
    /**
     * Create a LowPassFilter object.
     */
    public LowPassFilter() {
        alpha = new DoubleParameter(this, "Alpha", 0.5, 0., 1.);
    }

    public Double[] apply(Double[] data) {
        Double[] result = new Double[data.length];
        result[0] = data[0];
        for(int i = 1; i < data.length; i++)
            result[i] = alpha.getValue() * data[i] + (1 - alpha.getValue()) * result[i - 1];
        return result;
    }

    public Parameter[] getParameters() {
        return new Parameter[]{alpha};
    }

    public String toString() {
        return "Low Pass Filter";
    }

    private DoubleParameter alpha;
}