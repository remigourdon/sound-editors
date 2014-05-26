import framework.Player;
import framework.Sound;
import framework.generators.*;

/**
 * Dummy class to test the BasicEditor with command line input.
 */
public class TestBasicEditor {
    public static void main(String[] args) {

        Generator[] gs = new Generator[]{
            new SineGenerator(),
            new SquareGenerator(),
            new TriangleGenerator(),
            new SawtoothGenerator(),
            new GuitarGenerator()
        };

        Player pl = new Player();

        if(args.length % 3 == 0) {
            for(int i = 0; i < args.length / 3; i++) {
                Sound sound = new Sound (
                    gs[i % (gs.length - 1)],
                    Double.parseDouble(args[3 * i]),
                    Double.parseDouble(args[3 * i + 1]),
                    Double.parseDouble(args[3 * i + 2]));

                pl.addSound(sound);
            }
        }

        BasicEditor be = new BasicEditor(pl);
    }
}