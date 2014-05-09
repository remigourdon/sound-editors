package framework.generators;

/**
 * Abstract class to represent a wave generator.
 */
public abstract class Generator {
    /**
     * Generates the signal with the given frequency and duration.
     * @param  f  the frequency of the sample in hertzs
     * @param  d  the duration of the sample in seconds
     * @param  a  the amplitude of the sample
     * @return    the generated sample as an array of doubles
     */
    public abstract double[] generate(double f, double d, double a);

    // Constants
    public static final int SAMPLE_RATE   = 44100;    // CD quality audio
}