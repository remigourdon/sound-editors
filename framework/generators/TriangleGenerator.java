package framework.generators;

/**
 * Extends the Generator abstract class to create triangle waves.
 */
public class TriangleGenerator extends Generator {
    public Double[] generate(Double f, Double d, Double a) {
        int N = (int)(Generator.SAMPLE_RATE * d);
        Double[] result = new Double[N+1];

        Double phase = 0.;

        for(int i = 0; i <= N; i++) {
            if(phase < Math.PI)
                result[i] = -a + (2 * a / Math.PI) * phase;
            else
                result[i] = 3 * a - (2 * a / Math.PI) * phase;

            phase += (2 * Math.PI * f) / Generator.SAMPLE_RATE;

            if(phase > (2 * Math.PI))
                phase -= 2 * Math.PI;
        }

        return result;
    }

    public String toString() {
        return "Triangle Wave";
    }
}