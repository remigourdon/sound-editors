


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
    public void update(Observable o, Object obj) {
	// Erase the curve

	// Build the new one

	// display the changes
    }

    /*-----------------*/
    /*BUILDING THE VIEW*/
    /*-----------------*/

    /**
     * Build a TemporalView.
     * @param Sound s The sound to be displayed
     */
    public TemporalView(Sound s){
	
	// Build the frame
	JFrame frame = new JFrame();
	frame.setBounds(200,200, 300, 130);
	System.out.println(System.getProperty("user.dir")); // Where .mp3 should be located
        

	// display the WAVEFORM ------------
	
            

	// ---------------------------------

	// display the whole frame
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setVisible(true);
	
    }

}
