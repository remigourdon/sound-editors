package framework.generators;

/**
 * Interface to represent a wave generator.
 */
public interface Generator {
    /**
     * Generates the signal with the given frequency and duration.
     * @param  f  the frequency of the sample in hertzs
     * @param  d  the duration of the sample in seconds
     * @param  sr the sample rate
     * @return    the generated sample as an array of bytes
     */
    public byte[] generate(int f, int d, int sr);
}