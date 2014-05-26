import framework.*;
import framework.generators.*;

public class TestEditor {
    public static void main(String[] args) {
        GuitarGenerator g = new GuitarGenerator();

        Player pl1 = new Player();

        if(args.length % 3 == 0) {
            for(int i = 0; i < args.length / 3; i++) {
                Sound sound = new Sound (
                    g,
                    Double.parseDouble(args[3 * i]),
                    Double.parseDouble(args[3 * i + 1]),
                    Double.parseDouble(args[3 * i + 2]));

                pl1.addSound(sound);
            }
        }

        BasicEditor be = new BasicEditor(pl1);

        Player       pl2 = new Player();
        MatrixEditor me = new MatrixEditor(pl2);
    }
}