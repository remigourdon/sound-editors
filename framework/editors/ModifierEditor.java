package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import framework.modifiers.Modifier;
import framework.parameters.Parameter;

/**
 * Editor for Modifier object.
 */
public class ModifierEditor extends JPanel {
    public ModifierEditor(Modifier m) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(Parameter p : m.getParameters()) {
            add(new JLabel(m.getName()));
            add(p.attachEditor());
        }
    }
}