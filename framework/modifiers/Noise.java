package framework.modifiers;

import java.util.Random;

import framework.Sound;
import framework.generators.Generator;
import framework.modifiers.Modifier;
import framework.parameters.Parameter;
import framework.parameters.DoubleParameter;

/**
 * Add noise to the Sound.
 */
public class Noise extends Modifier {
    /**
     * Create a Noise object.
     */
    public Noise(Sound s) {
        super(s);
        sound = s;
        amplitude = new DoubleParameter(this, "Amplitude", 0.5, 0., 100.);
    }

    public Double[] apply(Double[] data) {
        Random rand = new Random();

        Double d = (Double) sound.getParameterByName("Duration").getValue();
        Double a = amplitude.getValue();

        Double[] newData = new Double[data.length];

        for(int i = 0; i < (d.intValue() * Generator.SAMPLE_RATE); i++) {
            newData[i] = data[i] + a * rand.nextDouble();
            // Clip
            if (newData[i] < -1.0) newData[i] = -1.0;
            if (newData[i] > +1.0) newData[i] = +1.0;
        }

        return newData;
    }

    public Parameter[] getParameters() {
        return new Parameter[]{amplitude};
    }

    public String toString() {
        return "Noise";
    }

    private Sound           sound;
    private DoubleParameter amplitude;
}