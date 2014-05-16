import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.Observable;

import javax.swing.*;

/**
 *	TemporalView provides a View corresponding to the Observer Pattern (MVC ?!).
 *	It uses the SignalPanel class to draw a temporal representation of the model's buffer.
 *
 */
public class FFTView extends JPanel implements View {
    
	
	/**
     * Implementation of view's method.
     * 
     * The update method is called when a change occurs in the model (Sound).
     * @param Observable o   			The sound model.
     * @param Object     dataChanged 	Is a boolean indicating if the model has been modified.
     * @Override
     */
    public void update(Observable o, Object dataChanged) {
    	
		// Retrieving the sound model
		Sound s = (Sound) o;
		// Retrieving the sound buffer
		Double[] data = s.getData();
		
		
		if( (boolean) dataChanged) {
			this.drawData(data);
		}
    }

    /**
     * Build a TemporalView.
     * @param int h	Height of the panel.
     * @param int w Width of the panel.
     * @param Sound s The sound to be displayed.
     */
    public FFTView(int h, int w){

    	height = h;
    	width = w;
    	
    	
    	
    }
    
    /**
     * Main method of this class.
     * 
     * Draws the corresponding FFT output of the given data signal.
     * @param	Double	data	the data array of the signal
     */
    public void drawData(Double[] data) {

    	/* -- Convert Double[] to double[] --  */
    	double[] res= new double[data.length];
    	for (int i = 0; i < data.length; i++) {
            res[i] = data[i].doubleValue();
        }
		
    	
        /* -- APPLY THE ALGORITHM --  */
        
    	Fft fft = new Fft();
    	fft.transform(res, res); // Awesome job ! Thanks Nayuki Minase ! (see Fft.java)
    	
    	double[] output = new double[data.length];
    	output = res;
    	
    	/* - --------------- - - */
    	/* -------scale y------- */
    	/* - --------------- - - */
    	double maxVal = -1000000000;// will be the rest of the biggest value of the array
    	int cpt = -1; 
    	int indexMax = 0; // will be the index of the biggest value of the array
   	 	double ratio = 1;
   	 	double diff = 0.0;
	 	
    	
    	
   	 	// run through the whole data array
   	 	for(int j = 0 ; j < data.length ; j++) { // this doesn't do its job !!!
    		// find who has the highest value
   	 		System.out.println(""+ output[j]);
   	 		cpt++;
   	 		if( output[j]  > maxVal){
    			maxVal = output[j];
    			indexMax = cpt;
    		}
    		
        }
   	 	
   	 	ratio = (output[indexMax] - height)/output[indexMax];
    	
    	// if the highest value is lower than the panel
	   	if(output[indexMax] < height ) {
	   		// we'll lift it up !
	   		System.out.println("the highest value is lower than the panel");
	   		
	   	// Apply the ratio so that everyone gets increased proportionally
	    	for(int k = 0; k < data.length ; k++) {
	    		output[k] *= ratio;
	    	}	
	   	}
	   	else// if the highest value is higher than the panel
	   	{
	   		// we'll bring it down !
	   		System.out.println("the highest value is higher than the panel");
	   	 	
	   		// calculate the ratio so that the highest reach the top
	    	//ratio = (output[indexMax] - height )/ height; 
	    	System.out.println("highest value "+output[indexMax]);
	    	
	    	// Apply the ratio so that everyone gets lowered proportionally
	    	for(int k = 0; k < data.length ; k++) {
	    		output[k] = output[k]* (1 - ratio);
	    		//output[k] %= height;
	    	}
	    	/* - ---------------------- - - */
	    	/* -------scale y : DONE------- */
	    	/* - ---------------------- - - */	
	   	}
	   	
	   	
    	
    	/* -- DISPLAY --  */
    	
    	// Data display settings
    	g2.setColor(Color.WHITE);
    	g2.clearRect( 0, 0, width, height);
    	g.setColor(Color.GREEN);
    	g.clearRect(0, 0, width, height);
    	
    	
    	// run through the buffer
    	for(int i = 0 ; i < output.length ; i++) {
    		
    		double currentPoint = (double) output[i];
    		// MEMENTO : Line2D.Double( x1, y1, x2, y2)
    		// Previous point = (x1,y1) & Current point = (x2,y2)
    		
    		g2.draw( new Line2D.Double(
    				output.length - i ,
    				prevPoint + height ,
    				output.length - (i+1),
    				currentPoint + height
    				));
    		prevPoint = currentPoint;
    	};
    	
    	// drawing the x axis
    	g.draw( new Line2D.Double(
    			0 ,
				height -1,
				width,
				height -1
				));
    	
    	// drawing the y axis
    	g.draw( new Line2D.Double(
    			0 ,
				0,
				0,
				height
				));
    	repaint();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
    	// calling mother's method
    	super.paintComponent(g);
    	
		if(bufferedImage == null){
    		init();
    	}
		// ( Image, x, y, theOberserver )
    	g.drawImage(bufferedImage, 0, 0, this);
    }
    
    /**
     * Defines the fundamentals aspects settings
     * Helper method
     * 
     * 
     */
    private void init(){
    	// size settings
    	width = getWidth();
    	height = getHeight();
    	
    	// image settings :
    	// 8 bit
    	// Alpha Red Green Blue
    	bufferedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
    	
    	g2 = bufferedImage.createGraphics();
    	g = bufferedImage.createGraphics();
    	
    	g2.setBackground(Color.black);
    	
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    // the waveform display
    //private SignalPanel signalPanel;

    // Panel visual settings
    private int height;
    private int width;
    
    // Used to draw the sound
    private BufferedImage bufferedImage;
    public Graphics2D g2;
    
    // Used to draw the x and y axis
    public Graphics2D g;
    
    // Memorizing the y1 point (c.f drawData() )
    private double prevPoint;
    
}
