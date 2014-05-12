import javax.swing.JFrame;




/**
 *
 *
 */
public class TemporalView extends JFrame implements View {

    /*-----------------------------*/    
    /*OBSERVER PATTERN REQUIREMENTS*/
    /*-----------------------------*/
    
    /**
     * The update method is called when a change occurs in the model (theSound)
     * @param Observable o   The sound model
     * @param Object     obj The data array (corresponding to the sound)
     */
    public void update(Observable o, Object theData) {
    	
		// Retreiving the sound model
		Sound s = o;
		// Retreiving the sound buffer
		data = theData;
		
    }

    /**
     * Build a TemporalView.
     * @param Sound s The sound to be displayed
     */
    public TemporalView(){
	
	// Settings
    setTitle("Signal plotting");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 200);
    
    signal = new SignalPanel();
    getContentPane().add(signal);
    
    setVisible(true);
	
    }
    
    
    // the waveforw display
    SignalPanel signal;

}
