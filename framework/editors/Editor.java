package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.util.Observable;
import java.util.Observer;

import framework.Player;
import framework.generators.*;

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

        fillPrototyper();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(
            this.getContentPane(),
            BoxLayout.Y_AXIS));

        // Top panel
        JPanel topPanel     = new JPanel();
        JButton playAllButton  = new JButton(new ImageIcon("framework/editors/icons/play.png"));
        playAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.playAll();
            }
        });
        topPanel.add(playAllButton);

        add(topPanel);
        add(createCentralComponent());

        pack();
        setVisible(true);
    }

    public void update(Observable o, Object arg) {
        if(o == player) {

        }
    }

    /**
     * Create the central component of the editor.
     * @return the central component newly created
     */
    public abstract JComponent createCentralComponent();

    protected void fillPrototyper() {
        // Add basic generators to the prototyper
        GeneratorPrototyper.addGenerator(new SineGenerator());
        GeneratorPrototyper.addGenerator(new SquareGenerator());
        GeneratorPrototyper.addGenerator(new SawtoothGenerator());
        GeneratorPrototyper.addGenerator(new TriangleGenerator());
        GeneratorPrototyper.addGenerator(new GuitarGenerator());
    }

    protected Player player;
}