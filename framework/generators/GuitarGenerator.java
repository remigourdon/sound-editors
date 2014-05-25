package framework.generators;

import java.util.Arrays;

import framework.util.RingBuffer;

/**
 * A guitar string Generator based on the Karplus-Strong algorithm.
 */
public class GuitarGenerator extends Generator {
    public Double[] generate(Double f, Double d, Double a) {
        // Prepare the result array
        int         N       = (int)(Generator.SAMPLE_RATE * d);
        Double[]    result  = new Double[N];
        Arrays.fill(result, 0.);

        // Prepare the string array
        int Ns = (int)(Generator.SAMPLE_RATE / f);
        RingBuffer buffer = new RingBuffer(Ns);

        // Pluck the string
        // It is done by filling with random values between -0.5 and 0.5 (noise)
        for(int i = 0; i < Ns; i++)
            buffer.enqueue(Math.random() - 0.5);

        // Now we apply the Karplus-Strong algorithm
        // Product of the average of the two first samples and the energy decay factor
        for(int i = 0; i < N; i++) {
            buffer.enqueue((buffer.dequeue() + buffer.peek()) / 2 * ENERGY_DECAY_FACTOR);
            result[i] = buffer.peek();
        }

        return result;
    }

    public String toString() {
        return "Guitar";
    }

    Double ENERGY_DECAY_FACTOR = 0.996;
}