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
     * @return    the generated sample as an array of Doubles
     */
    public abstract Double[] generate(Double f, Double d, Double a);

    /**
     * Get the prototypes of the generators.
     * @return an array containing prototypes
     */
    public static Generator[] getPrototypes() {
        Generator[] prototypes = new Generator[1];
        prototypes[0] = new SineGenerator();
        return prototypes;
    }

    public abstract String toString();

    // Constants
    public static final int SAMPLE_RATE   = 44100;    // CD quality audio
}