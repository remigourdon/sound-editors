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
public class TemporalView extends JPanel implements View {
    
	
	/**
     * Implementation of view's method.
     * 
     * 
     * The update method is called when a change occurs in the model (theSound).
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
    public TemporalView(int h, int w){

    	height = h;
    	width = w;
    	
    }
    
    /**
     * Main method of this class.
     * Draws the waveform.
     * @param	Double	buffer	the data array to be plotted
     */
    public void drawData(Double[] data/*buffer*/) {
/* This is how to convert Double[] to double[]
 * 		//Converts an array of object Characters to primitives.	
    	public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }
  */
    	/*
    	double[] buffer = new double[data.length];
        double[] in = new double[data.length];
        
        */
    	int fft_size = 4096;
        int slope = fft_size / 2; //how much do we move the window
		int frames = (int)(2 / slope) - 1;//getting number of frames, discard last part if minor than size. Not ideal
    	
/*

    	FFT fft = new FFT(fft_size);
        fft.makeWindow();
    	double[] buffer = new double[fft_size];
    	double[] in = new double[fft_size];
    	
    	
    	// fill it
    	for (int i = 0; i < data.length; i++) {
            in[i] = data[i].longValue();
            //System.out.println(""+data[i]);
        }
        
		fft.fft( in, buffer);
*/		
	

		//FFT
		FFT fft = new FFT(fft_size);
		double[] window = fft.getWindow();
		double[] re = new double[fft_size]; //array for the real part
		double[] im = new double[fft_size];	//array for the imaginary part. NOT USED FOR NOW
		
		float[] centroids = new float[frames]; //array for the centroids of the entire file
		float[] energies = new float[frames]; //array for the energies of the entire file
		
		for(int f = 0; f < frames; f++){
			//copy slide of signal to re normalized to -1 to 1
			//fill im with zeros
			//multiply signal for window
			for(int i = 0; i < fft_size; i++){
				re[i] = music[(f * slope) + i] * 1.0 / (Short.MAX_VALUE + 1);
				re[i] *= window[i];
				im[i] = 0;
			}
			fft.fft(re, im); //call of the magic function! :-) Thanks to the MeapSoft guys!

			//copy fft data to the array and send to other thread. Remember, Only half of the array is necessary
			ArrayList<Float> fftData = new ArrayList<Float>();
			fftData.add(new Float(2)); //heather TWO for fft
			fftData.add(new Float(frames)); //keep track of how many frames
			fftData.add(new Float(fft_size / 2)); //size of fft_window
			fftData.add(new Float(f)); //counter of current frame
			
			//these two values are used for getting the Spectral Centroid
			//http://en.wikipedia.org/wiki/Spectral_centroid
			double centroidFnXn = 0;
			double centroidXn = 0;
			
			double energy = 0;
			
			//after heuristic observation it seems the max that the fft can give is around 70
			//this for loop is where we traverse all the bands of the FFT -it can be expensive
			for(int i = 0; i < fft_size / 2; i++){
				fftData.add((float)Math.sqrt(Math.abs(re[i]))); //remove negatives
				
				//for the centroid we keep value of the 
				centroidFnXn += i * Math.abs(re[i]); //keeping track of the magnitude of each bin multipied by it's index, it does the trick!
				centroidXn += Math.abs(re[i]); //keep track of the total value of the addition of the magnitudes of all the bins
				
				//For the energy we just add all the values of all the bands
				energy += Math.sqrt(Math.abs(re[i])); //use of the Math.sqrt as a way to compress the values into a smaller range
			}
			
			centroids[f] = (float)(centroidFnXn / centroidXn);

			energies[f] = (float)(energy);
			
			publishProgress(fftData);
		}
		
    	// Data display settings
    	g2.setColor(Color.WHITE);
    	g2.clearRect( 0, 0, width, height);
    	
    	// run through the buffer
    	for(int i = 0 ; i < buffer.length ; i++) {
    		double prevPoint = (double) buffer[i];
    		// MEMENTO : Line2D.Double( x1, y1, x2, y2)
    		// Previous point = (x1,y1) & Current point = (x2,y2)
    		g2.draw( new Line2D.Double(
    				buffer.length - i ,
    				currentPoint + height/2 ,
    				buffer.length - (i+1),
    				prevPoint + height/2
    				));
    		
    		//System.out.println("Buffer = "+ buffer[i]);
    		currentPoint = prevPoint;
    		
    	}
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
    
    // Memorizing the y1 point (c.f drawData() )
    private double currentPoint;
    
}
