package framework.views;

import java.util.Observable;
import javax.swing.*;
import java.util.Observer;


/**
 *	TemporalView provides a View corresponding to the Observer Pattern (MVC ?!).
 *	It uses the SignalPanel class to draw a temporal representation of the model's buffer.
 *
 */
public class TemporalView extends JFrame implements View {
    
    /**
     * Implementation of view's method.
     * 
     * 
     * The update method is called when a change occurs in the model (theSound).
     * @param Observable o   			The sound model.
     * @param Object     dataChanged 	Is a boolean indicating if the buffer has been modified.
     * @Override
     */
    public void update(Observable o, Object dataChanged) {
    	
		// Retrieving the sound model
		Sound s = (Sound) o;
		// Retrieving the sound buffer
		Double[] data = s.getData();
		
		
		if( (boolean) dataChanged) { // Might not be required
			signalPanel.drawData(data);
		}
		
    }

    /**
     * Build a TemporalView.
     * @param Sound s The sound to be displayed.
     */
    public TemporalView(){
	
	// Settings
    setTitle("Signal plotting");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 200);
    
    signalPanel = new SignalPanel();
    getContentPane().add(signalPanel);
    
    setVisible(true);
	
    }
    
    
    // the waveform display
    private SignalPanel signalPanel;

}
