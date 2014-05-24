package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import framework.Sound;
import framework.generators.Generator;
import framework.generators.GeneratorPrototyper;
import framework.parameters.Parameter;
import framework.modifiers.Modifier;

/**
 * Editor for Sound object.
 *
 * Allow the user to modify the frequency, duration and amplitude.
 */
public class SoundEditor extends JFrame {
    /**
     * Create a SoundEditor object.
     * @param  s the associated Sound
     */
    public SoundEditor(Sound s) {
        super("Settings");
        sound   = s;

        getContentPane().setLayout(new BoxLayout(
            this.getContentPane(),
            BoxLayout.Y_AXIS));

        ////////////////////////////
        // Basic parameters panel //
        ////////////////////////////
        add(new JLabel("General"));

        JPanel basicPanel = new JPanel();

        // Generator selector
        final JComboBox generatorsList = new JComboBox(GeneratorPrototyper.getPrototypes());
        generatorsList.setSelectedItem(sound.getGenerator());
        generatorsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setGenerator((Generator) generatorsList.getSelectedItem());
            }
        });
        basicPanel.add(generatorsList);

        // Generic parameters
        for(Parameter p : s.getParameters()) {
            basicPanel.add(p.attachEditor());
        }

        add(basicPanel);


        /////////////////////
        // Modifiers panel //
        /////////////////////
        JPanel modifPanel = new JPanel();
        modifPanel.setLayout(new BoxLayout(modifPanel, BoxLayout.Y_AXIS));

        modifPanel.add(new JLabel("Modifiers"));

        for(Modifier m : sound.getModifiers()) {
            modifPanel.add(m.attachEditor());
        }

        add(modifPanel);


        pack();
        setVisible(true);
    }

    private Sound                       sound;
}