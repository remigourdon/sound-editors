package framework.generators;

import java.util.Arrays;

import framework.util.RingBuffer;
import framework.util.GuitarString;

/**
 * A guitar string Generator based on the Karplus-Strong algorithm.
 */
public class GuitarGenerator extends Generator {
    public Double[] generate(Double f, Double d, Double a) {
        int         N       = (int)(Generator.SAMPLE_RATE * d);
        Double[]    result  = new Double[N];

        GuitarString string = new GuitarString(f);

        string.pluck();

        for(int i = 0; i < N; i++) {
            result[i] = string.sample();
            string.tic();
        }

        return result;
    }

    public String toString() {
        return "Guitar";
    }
}