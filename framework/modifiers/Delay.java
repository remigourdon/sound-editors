package framework.modifiers;

import framework.modifiers.Modifier;
import framework.parameters.Parameter;
import framework.parameters.DoubleParameter;
import framework.generators.Generator;

/**
 * Implements a delay effect.
 */
public class Delay extends Modifier {
    /**
     * Create a Delay object.
     */
    public Delay() {
        delay = new DoubleParameter(this, "Delay", 0., 0., 60.);
        decay = new DoubleParameter(this, "Decay", 0., 0., 1.);
    }

    public Double[] apply(Double[] data, Double f, Double d) {
        Double[] result     = new Double[data.length];
        int delaySamples    = (int) (delay.getValue() * Generator.SAMPLE_RATE);

        for(int i = 0; i < data.length - delaySamples; i++) {
            result[i + delaySamples] = data[i] * decay.getValue();
        }

        return result;
    }

    public Parameter[] getParameters() {
        return new Parameter[]{delay, decay};
    }

    public String toString() {
        return "Delay";
    }

    DoubleParameter delay;
    DoubleParameter decay;
}