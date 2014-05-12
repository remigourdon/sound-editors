import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.util.Observable;
import java.util.Observer;
import framework.Player;

/**
 * Abstract class to support the main window of the application.
 *
 * It is aimed to create all the necessary components for the Player.
 */
public abstract class Editor extends JFrame implements Observer {
    /**
     * Create an Editor object.
     * @param  pl   the associated Player
     * @param  name the name to give to the window
     */
    public Editor(Player pl, String name) {
        super(name);
        player = pl;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(
            this.getContentPane(),
            BoxLayout.Y_AXIS));

        pack();
        setVisible(true);
    }

    public void update(Observable o, Object arg) {
        if(o == player) {

        }
    }

    private Player player;
}