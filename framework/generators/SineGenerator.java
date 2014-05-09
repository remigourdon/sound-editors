package framework.generators;

/**
 * Extends the Generator abstract class to create sine waves.
 */
public class SineGenerator extends Generator {
    public double[] generate(double f, double d, double a) {
        int N = (int)(Generator.SAMPLE_RATE * d);
        double[] result = new double[N+1];

        for(int i = 0; i <= N; i++)
            result[i] = a * Math.sin(2 * Math.PI * i * f / Generator.SAMPLE_RATE);

        return result;
    }
}