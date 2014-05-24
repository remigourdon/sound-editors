package framework.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComponent;
import java.util.Observable;
import java.util.Observer;

import framework.parameters.Parameter;

/**
 * Class representing an editor for Parameter object.
 *
 * Allow the user to modify the value of the parameter.
 */
public class ParameterEditor extends JPanel implements Observer {
    /**
     * Create a ParameterEditor object.
     * @param  p the associated parameter
     */
    public ParameterEditor(Parameter p) {
        parameter = p;

        field = new JTextField(parameter.getValue().toString());
        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parameter.setValue(field.getText());
            }
        });

        add(new JLabel(p.getName()));
        add(field);
    }

    public void update(Observable o, Object arg) {
        if(o == parameter) {
            field.setText(parameter.getValue().toString());
        }
    }

    /**
     * Get the associated Parameter.
     * @return the associated Parameter
     */
    public Parameter getParameter() {
        return parameter;
    }

    private Parameter   parameter;
    private JTextField  field;
}