package framework.modifiers;

import java.util.Arrays;

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
        decay = new DoubleParameter(this, "Decay", 1., 0., 1.);
    }

    public Double[] apply(Double[] data) {
        Double[] result     = new Double[data.length];
        Arrays.fill(result, 0.);
        int delaySamples    = (int) (delay.getValue() * Generator.SAMPLE_RATE);

        for(int i = 0; i < data.length - delaySamples; i++) {
            result[i + delaySamples] = data[i] * decay.getValue();
        }

        assert result.length == data.length:
            "violated postcondition: result.length == data.length";
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