import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import framework.editors.Editor;
import framework.Sound;
import framework.Player;
import framework.views.TemporalView;

/**
 * Basic implementation of the framework.
 */
public class BasicEditor extends Editor {
    public BasicEditor(Player pl) {
        super(pl, "Basic Editor");
    }

    public JComponent createCentralComponent() {
        JPanel centralComponent = new JPanel();
        centralComponent.setLayout(new BoxLayout(
            centralComponent,
            BoxLayout.Y_AXIS));

        // Play all button
        JButton playAllButton  = new JButton(new ImageIcon("framework/editors/icons/play.png"));
        playAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.playAll();
            }
        });
        add(playAllButton);

        int i = 0;
        for(Sound s : player.getAllSounds()) {
            final Sound sound = s;

            JPanel linePanel = new JPanel();

            JLabel label = new JLabel("Sound " + i);
            linePanel.add(label);

            JButton playButton = new JButton(new ImageIcon("framework/editors/icons/play.png"));
            playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    player.play(sound);
                }
            });
            linePanel.add(playButton);

            TemporalView v = new TemporalView(200, 100);
            s.attachView(v);
            linePanel.add(v);

            JButton paramButton = new JButton(new ImageIcon("framework/editors/icons/settings.png"));
            paramButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sound.attachEditor();
                }
            });
            linePanel.add(paramButton);

            centralComponent.add(linePanel);

            i++;
        }

        return centralComponent;
    }
}