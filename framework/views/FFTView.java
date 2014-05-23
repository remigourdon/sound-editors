import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Observable;

import framework.sound

import javax.swing.*;

/**
 *	TemporalView provides a View corresponding to the Observer Pattern.
 *	It uses the Fft class to draw a Fast Fourier transform of the model's buffer.
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
     * Build a FFT view.
     * @param int h	Height of the panel.
     * @param int w Width of the panel.
     */
     public FFTView(int h, int w){
    	height = h;
    	width = w;  	
    }


     /*
      * Helper method that scales the results to the width,height of the frame.
      * @param	double[] 	signal	The array to be scaled
      * @return 	double[] 	output 	The scaled array
      */
     private double[] scale(double[] signal) {
     	
     	// The index corresponds to the frequency
     	
     	double[] output = new double[signal.length];
     	output = signal.clone();
     	
     	
     	double maxVal = -1000000000;	// highest value of the array
     	double minVal = 1000000000;		// lowest value of the array
     	int indexMax = 0; // Index of the biggest value of the array
	 	int indexMin = 0; // Index of the lowest value of the array
	 
	 	// find the index of the highest value
	 	for(int j = 0 ; j < output.length ; j++) {
	 		System.out.println(""+output[j]);
	 		if( output[j]  > maxVal){
	 			maxVal = output[j];
	 			indexMax = j;
	 		}
	 	}
	 	
	 	System.out.println("Maximum value :" +maxVal);
	 	System.out.println("indexMax :" +indexMax);
 	
	 	// setting the ratios
	 	//double yRatio = output[indexMax]/height;
	 	double yRatio = output[indexMax] / height;
	 			
     	// where the first harmonic should be moved
     	double ref = width /10;
     	
     	/* if the index (frequency) of the first harmonic is superior to the reference (x scaling) */
     	
     	if(indexMax > ref ) {
     		// Move each element to the left
 	    	for( int i = indexMax; indexMax > ref ; indexMax--) {
     			double tmp = output[indexMax];
 	    		output[indexMax] = output[indexMax+1];
 	    		output[indexMax-1]= tmp;
 	    	}
 	    	System.out.println("X Scaling : DONE");
 	    }
     	System.out.println("indexMax after X scaling :"+indexMax);
     	
     	
	 	// if the highest point is higher than the height of the panel (y scaling)
	 	if(maxVal > height) {
	 		// apply the Y ratio
	    	for(int k = 0; k < output.length ; k++) {
	    		output[k] /= (yRatio);
	    		System.out.println(""+output[k]);
	    	}
	    	System.out.println("Y Scaling : DONE");
	 	}
	 	System.out.println("maxValue after Y scaling :"+output[indexMax]);
	 	System.out.println("and is at index :"+indexMax+ "(should be ="+ref+" ) sinon le X scaling ne fonctionne pas");
 		return output;
 	}     
     
    /**
     * Main method of this class.
     * 
     * Draws the corresponding FFT output of the given data signal.
     * @param	Double	data		the data array of the signal
     */
    public void drawData(Double[] data) {
    	
    	// -- Convert Double[] to double[] -- 
    	// because transform() only takes doubles
    	double[] res= new double[data.length];
    	for (int i = 0; i < data.length; i++) {
            res[i] = data[i].doubleValue();
        }
    	double[] output = new double[data.length];
    	output = res;

    	// -- APPLY THE ALGORITHM --  
    	
    	Fft fft = new Fft();
    	fft.transform(res, res); // Awesome job ! Thanks to Nayuki Minase ! (see Fft.java)
    	
    	// -- ------------------- --  

    	
    	// scaling
    	output = scale(output);
    	
    	// get rid of the imaginary part
    	for (int i = 0; i < output.length; i++) {
            if(output[i] > 0)
            	output[i] = 0;
        }
    	/*
    	for(int i = 0 ; i < output.length ; i++) {
    		output[i] *= (-1); 
    	}
    	*/
    	
    	
    	
    	/* -- DISPLAY --  */
    	
    	// Data display settings

    	g2.setColor(Color.WHITE);
    	g2.clearRect( 0, 0, width, height);
    	g.setColor(Color.GREEN);
    	g.clearRect( 0, 0, width, height);
    	
    	// run through the buffer
    	for(int i = 0 ; i < output.length ; i++) {
    		double currentPoint = (double) output[i];
    		// MEMENTO : Line2D.Double( x1, y1, x2, y2)
    		// Previous point = (x1,y1) & Current point = (x2,y2)
    		g2.draw( new Line2D.Double(
    				output.length - i ,
    				prevPoint + height/2 ,
    				output.length - (i+1),
    				currentPoint + height/2
    				));
    		
    		prevPoint = currentPoint;
    		
    	}
    	// adding the x axis
    	g.draw( new Line2D.Double(
    			0 ,
				height/2,
				width,
				height/2
				));
    	repaint();
    	
    	
    	
    	/*
    	// plot the whole buffer
    	for(int i = 0 ; i < output.length ; i++) {
    		
    		double currentPoint = (double) output[i];
    		// MEMENTO : Line2D.Double( x1, y1, x2, y2)
    		// Previous point = (x1,y1) & Current point = (x2,y2)
    		
    		// links the previous point to the current point
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
    	
    	repaint();// calls paintComponent()
    	
    	*/
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
    	
    	// creates a Graphics2D to draw into the BufferedImage
    	g2 = bufferedImage.createGraphics();
    	g = bufferedImage.createGraphics();
    	// now we can draw using g2 and g and the method draw()
    	
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