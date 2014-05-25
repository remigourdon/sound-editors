package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import framework.Sound;
import framework.generators.Generator;
import framework.generators.GeneratorPrototyper;
import framework.parameters.Parameter;
import framework.modifiers.Modifier;
import framework.modifiers.ModifierPrototyper;

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
        // Set the selected item to be the current Sound Generator
        for(Generator g : GeneratorPrototyper.getPrototypes()) {
            if(g.getClass().getName() == sound.getGenerator().getClass().getName())
                generatorsList.setSelectedItem(g);
        }
        generatorsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected Generator
                Generator sg = (Generator) generatorsList.getSelectedItem();

                // Clone it and give it to the Sound object
                sound.setGenerator((Generator) sg.clone());
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

        // New modif panel
        final JPanel newModifPanel = new JPanel();
        final JComboBox modifiersList = new JComboBox(ModifierPrototyper.getPrototypes());
        JButton newModifButton = new JButton(new ImageIcon("framework/editors/icons/plus.png"));
        newModifButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected Modifier
                Modifier sm = (Modifier) modifiersList.getSelectedItem();

                // Clone it
                Modifier m = (Modifier) sm.clone();

                // Add to the sound
                sound.addModifier(m);

                // Attach editor and add to the JPanel
                newModifPanel.add(m.attachEditor());
            }
        });
        newModifPanel.add(modifiersList);
        newModifPanel.add(newModifButton);
        modifPanel.add(newModifPanel);

        // Append the modifiers already attached to the Sound
        for(Modifier m : sound.getModifiers()) {
            modifPanel.add(m.attachEditor());
        }

        add(modifPanel);

        pack();
        setVisible(true);
    }

    private Sound                       sound;
}