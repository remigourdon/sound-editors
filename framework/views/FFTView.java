package framework.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
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
			this.drawData(data, s.getFrequency());
		}
    }

    /**
     * Build a TemporalView.
     * @param int h	Height of the panel.
     * @param int w Width of the panel.
     */
    public FFTView(int h, int w){

    	height = h;
    	width = w;
    }
    
    /**
     * Main method of this class.
	 * 
     * Draws the corresponding FFT output of the given data signal.
     * @param	Double	data		the data array of the signal
     * @param 	Double 	frequency	the frequency of the signal (Hz)
     */
    public void drawData(Double[] data, Double frequency) {

    	
    	/* -- Convert Double[] to double[] --  */
    	// transform() only takes doubles
    	double[] res= new double[data.length];
    	for (int i = 0; i < data.length; i++) {
            res[i] = data[i].doubleValue();
        }
		
    	
    	
        /* -- APPLY THE ALGORITHM --  */
    	Fft fft = new Fft();
    	fft.transform(res, res); // Awesome job ! Thanks to Nayuki Minase ! (see Fft.java)
    	double[] output = new double[data.length];
    	output = res;
    	/* -- ------------------- --  */
    	
    	// i don't know why but transform returns some negatives, get rid of them here
    	for (int i = 0; i < output.length; i++) {
            if(output[i] < 0) output[i]= 0;
        }
    	
    	// scaling
    	output = scale(output,  frequency.doubleValue() );
    	
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
    	
    	repaint();// calls paintComponent()
    }

    /*
     * Helper method that scales the results to the width,height of the frame.
     * @param	double[] 	signal	The array to be scaled
     * @param	double		freq	The frequency of the signal
     * @return 	double[] 	output 	The scaled array
     */
    private double[] scale(double[] signal, double freq) {
    	
    	double[] output = signal;
    	
    	
    	double maxVal = -1000000000;	// highest value of the array
    	double minVal = 1000000000;		// lowest value of the array
    	int indexMax = 0; // Index of the biggest value of the array
   	 	int indexMin = 0; // Index of the lowest value of the array
   	 
   	 	// find who has the highest value
   	 	for(int j = 0 ; j < output.length ; j++) {
   	 		if( output[j]  > maxVal){
    			maxVal = output[j];
    			indexMax = j;
    		}
        }
   	 	System.out.println("Bigest value :"+maxVal);
   	 	System.out.println("Its Index :"+indexMax);
   	 	
   	 	// find who has the lowest value
   	 	for(int j = 0 ; j < output.length ; j++) {
   	 		if( output[j]  < minVal){
    			minVal = output[j];
    			indexMin = j;
    		}
        }
   	 	System.out.println("Lowest value :"+minVal);
   	 	System.out.println("Its index :"+indexMin);
   	 	
    	// setting the ratios
    	double xRatio = output.length/width;
    	double yRatio = output[indexMax]/height;
    	
    	// apply the x ratio
    	double ref = 20;
    	for (int i = indexMax ; indexMax != ref ; indexMax--) {
    		output[i-1]= output[i];
    		
    	}
    	
    	System.out.println("Bigest value :"+output[indexMax]);//doit afficher ref comme index 
   	 	System.out.println("Its Index :"+indexMax);
   	 	
    	// apply the Y ratio
    	for(int k = 0; k < output.length ; k++) {
    		output[k] /= yRatio;
    	}
    	
    	System.out.println("xRatio = "+xRatio);
    	System.out.println("yRatio = "+yRatio);
    	System.out.println("maxVal = "+maxVal);
    	System.out.println("must equals maxVal = "+output[(int) ref]);
		return output;
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
