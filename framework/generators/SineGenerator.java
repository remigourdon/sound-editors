/**
 * Implements the Generator interface to create sine waves.
 */
public class SineGenerator Implements Generator {
    public byte[] generate(int f, double d, int sr) {
        byte[] sin = new byte[d / 1000 * sr];

        double samplingInterval = (double) (sr / f);

        for(int i = 0; i < sin.length; i++) {
            double angle = (2.0 * Math.PI * i) / samplingInterval;
            sin[i] = (byte) (Math.sin(angle) * 127);
        }
        return sin;
    }
}