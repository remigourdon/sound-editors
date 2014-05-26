import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import framework.editors.Editor;
import framework.Sound;
import framework.Player;
import framework.generators.GuitarGenerator;
import framework.views.TemporalView;
import framework.views.FFTView;


/**
 * Advanced implementation of the framework, as a Sound matrix.
 */
public class MatrixEditor extends Editor {
    public MatrixEditor(Player pl) {
        super(pl, "Matrix Editor");
    }

    public JComponent createCentralComponent() {
        matrix = new HashMap<Sound, JCheckBox[]>();

        JPanel centralComponent = new JPanel();
        centralComponent.setLayout(new BoxLayout(
            centralComponent,
            BoxLayout.Y_AXIS));

        // Play matrix button
        JButton playMatrixButton  = new JButton(new ImageIcon("framework/editors/icons/play.png"));
        playMatrixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < COLS; i++) {
                    ArrayList<Sound> selection = new ArrayList<Sound>();
                    for(Sound s : matrix.keySet()) {
                        // If the checkbox is checked
                        // Add to the selection
                        if(matrix.get(s)[i].isSelected())
                            selection.add(s);
                    }
                    player.playSelection(selection);
                }
            }
        });
        add(playMatrixButton);

        Double[] FREQS = new Double[]{82.4, 110., 146.8, 196., 246.9, 329.6};

        for(int i = 0; i < FREQS.length; i++) {
            GuitarGenerator g = new GuitarGenerator();
            Sound newSound = new Sound(g, FREQS[i], 1., 1.);
            player.addSound(newSound);
            centralComponent.add(createMatrixLine(newSound));
        }

        return centralComponent;
    }

    public JPanel createMatrixLine(Sound s) {
        final Sound sound = s;
        JPanel  matrixLine      = new JPanel();

        // Views
        TemporalView v = new TemporalView(200, 100);
        s.attachView(v);
        matrixLine.add(v);

        FFTView fft_v = new FFTView(200, 100);
        s.attachView(fft_v);
        matrixLine.add(fft_v);

        // Play button
        JButton playButton  = new JButton(new ImageIcon("framework/editors/icons/play.png"));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.play(sound);
            }
        });
        matrixLine.add(playButton);

        // Settings button
        JButton settingsButton  = new JButton(new ImageIcon("framework/editors/icons/settings.png"));
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.attachEditor();
            }
        });
        matrixLine.add(settingsButton);

        // Checkboxes
        JCheckBox[] cbs = new JCheckBox[COLS];
        for(int i = 0; i < COLS; i++) {
            JCheckBox cb = new JCheckBox();
            cbs[i] = cb;
            matrixLine.add(cb);
        }

        matrix.put(sound, cbs);

        return matrixLine;
    }

    final int COLS = 12;

    HashMap<Sound, JCheckBox[]> matrix;
}