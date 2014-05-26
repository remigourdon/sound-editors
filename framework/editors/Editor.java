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
import framework.modifiers.*;

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

        fillGeneratorPrototyper();
        fillModifierPrototyper();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(
            this.getContentPane(),
            BoxLayout.Y_AXIS));

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

    /**
     * Fill the GeneratorPrototyper with standard generators.
     *
     * This method should be overloaded to integrate custom generators.
     */
    protected void fillGeneratorPrototyper() {
        // Add basic generators to the prototyper
        GeneratorPrototyper.addGenerator(new SineGenerator());
        GeneratorPrototyper.addGenerator(new SquareGenerator());
        GeneratorPrototyper.addGenerator(new SawtoothGenerator());
        GeneratorPrototyper.addGenerator(new TriangleGenerator());
        GeneratorPrototyper.addGenerator(new GuitarGenerator());
    }

    /**
     * Fill the ModifierPrototyper with standard modifiers.
     *
     * This method should be overloaded to integrate custom modifiers.
     */
    protected void fillModifierPrototyper() {
        // Add basic modifiers to the prototyper
        ModifierPrototyper.addModifier(new Delay());
        ModifierPrototyper.addModifier(new LowPassFilter());
    }

    protected Player player;
}