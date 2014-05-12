package framework.editors;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import framework.Sound;

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
        sound = s;

        // Frequency input
        add(new JLabel("Frequency:"));
        frequencyField  = new JTextField(sound.getFrequency().toString());
        add(frequencyField);

        // Duration input
        add(new JLabel("Duration:"));
        durationField   = new JTextField(sound.getDuration().toString());
        add(durationField);

        // Amplitude input
        add(new JLabel("Amplitude:"));
        amplitudeField  = new JTextField(sound.getAmplitude().toString());
        add(amplitudeField);
    }

    private Sound sound;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField amplitudeField;
}