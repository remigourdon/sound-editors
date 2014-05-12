package framework.generators;

/**
 * Extends the Generator abstract class to create sine waves.
 */
public class SineGenerator extends Generator {
    public Double[] generate(Double f, Double d, Double a) {
        int N = (int)(Generator.SAMPLE_RATE * d);
        Double[] result = new Double[N+1];

        for(int i = 0; i <= N; i++)
            result[i] = a * Math.sin(2 * Math.PI * i * f / Generator.SAMPLE_RATE);

        return result;
    }
}