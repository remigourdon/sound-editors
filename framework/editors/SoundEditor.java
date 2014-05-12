package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.util.Observable;
import java.util.Observer;

import framework.Sound;
import framework.generators.Generator;

/**
 * Editor for Sound object.
 *
 * Allow the user to modify the frequency, duration and amplitude.
 */
public class SoundEditor extends JPanel implements Observer {
    /**
     * Create a SoundEditor object.
     * @param  s the associated Sound
     */
    public SoundEditor(Sound s) {
        sound = s;

        // Generator selector
        JComboBox generatorsList = new JComboBox(Generator.getPrototypes());
        generatorsList.setSelectedItem(sound.getGenerator());
        generatorsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setGenerator(generatorsList.getSelectedItem());
            }
        });
        add(generatorsList);

        // Frequency input
        add(new JLabel("Frequency:"));
        frequencyField  = new JTextField(sound.getFrequency().toString());
        frequencyField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setFrequency(Double.parseDouble(frequencyField.getText()));
            }
        });
        add(frequencyField);

        // Duration input
        add(new JLabel("Duration:"));
        durationField   = new JTextField(sound.getDuration().toString());
        durationField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setDuration(Double.parseDouble(durationField.getText()));
            }
        });
        add(durationField);

        // Amplitude input
        add(new JLabel("Amplitude:"));
        amplitudeField  = new JTextField(sound.getAmplitude().toString());
        amplitudeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.setAmplitude(Double.parseDouble(amplitudeField.getText()));
            }
        });
        add(amplitudeField);
    }

    public void update(Observable o, Object arg) {
        if(o == sound) {
            frequencyField.setText(sound.getFrequency().toString());
            durationField.setText(sound.getDuration().toString());
            amplitudeField.setText(sound.getAmplitude().toString());
        }
    }

    private Sound sound;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField amplitudeField;
}