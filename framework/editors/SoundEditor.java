package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.util.Observable;
import java.util.Observer;

import framework.Sound;
import framework.generators.Generator;
import framework.generators.GeneratorPrototyper;
import framework.parameters.Parameter;

/**
 * Editor for Sound object.
 *
 * Allow the user to modify the frequency, duration and amplitude.
 */
public class SoundEditor extends JPanel {
    /**
     * Create a SoundEditor object.
     * @param  s the associated Sound
     */
    public SoundEditor(Sound s) {
        sound   = s;

        // Generator selector
        final JComboBox generatorsList = new JComboBox(GeneratorPrototyper.getPrototypes());
        generatorsList.setSelectedItem(sound.getGenerator());
        generatorsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setGenerator((Generator) generatorsList.getSelectedItem());
            }
        });
        add(generatorsList);

        // Generic parameters
        for(Parameter p : s.getParameters()) {
            add(p.attachEditor());
        }
    }

    private Sound                       sound;
}