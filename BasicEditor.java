import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import framework.editors.Editor;
import framework.Sound;
import framework.Player;

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

        for(Sound s : player.getAllSounds())
            centralComponent.add(s.attachEditor());

        return centralComponent;
    }
}