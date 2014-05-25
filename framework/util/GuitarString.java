package framework.util;

import framework.util.RingBuffer;
import framework.generators.Generator;

/**
 * Models a guitar string using the Karplus-Strong algorithm.
 */
public class GuitarString {
    /**
     * Create a GuitarString of the given frequency.
     * @param  f the frequency
     */
    public GuitarString(Double f) {
        N = (int) (Generator.SAMPLE_RATE / f);

        buffer = new RingBuffer(N);

        // Guitar string at rest
        for(int i = 0; i < N; i++)
            buffer.enqueue(0.);
    }

    /**
     * Models the plucking of the string.
     */
    public void pluck() {
        for(int i = 0; i < N; i++) {
            // Enqueue random value between -0.5 and 0.5 (noise)
            buffer.enqueue(Math.random() - 0.5);
        }
    }

    /**
     * Apply the Karplus-Strong update.
     */
    public void tic() {
        Double first = buffer.dequeue();
        buffer.enqueue((first * buffer.peek()) * 0.5 * ENERGY_DECAY_FACTOR);
    }

    /**
     * Get the value at the front of the buffer.
     * @return the value at the front of the buffer
     */
    public Double sample() {
        return buffer.peek();
    }

    RingBuffer      buffer;
    int             N;
    final Double    ENERGY_DECAY_FACTOR = 0.996;
}