import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import javax.swing.JPanel;

import framework.Sound;
import framework.views.View;


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
		add(this);
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

    	// get rid of the imaginary part
    	for (int i = 0; i < output.length; i++) {
            if(output[i] < 0)
            	output[i] = 0;
        }



    	// -- Data display settings --

    	g2.setColor(Color.WHITE);
    	g2.clearRect( 0, 0, width, height);
    	g.setColor(Color.RED);
    	g.clearRect( 0, 0, width, height);


    	// set the origin on the down-left corner
    	g2.translate(0, height - 1);

    	// set the line's width
    	g2.setStroke(new BasicStroke(80));

    	// Pick the scale in function of the size of the panel
    	if(height <= 200 && width <= 400){
    		// Scale format : (125;300) (h;w)
        	g2.scale(0.005, -0.0002);

    	}
    	else{
    		// Scale format : (250;600) (h;w)
        	g2.scale(0.06, -0.0002);
    	}

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

    	// drawing the y axis
    	g.draw( new Line2D.Double(
    			0 ,
				0,
				0,
				height
				));

    	// adding the x axis
    	g.draw( new Line2D.Double(
    			0 ,
				height -1,
				width,
				height -1
				));

    	repaint();// calls paintComponent()

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

    // Used to draw anything
    private BufferedImage bufferedImage;

    // Used to draw the sound
    private Graphics2D g2;

    // Used to draw the x and y axis
    private Graphics2D g;

    // Memorizing the y1 point (c.f drawData() )
    private double prevPoint;

}