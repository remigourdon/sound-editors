import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
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
     * Build a FFT view.
     * @param int h	Height of the panel.
     * @param int w Width of the panel.
     */
     public FFTView(int w, int h){
	 // panel size settings
    	width = w;
    	height = h;

	// build the bufferImage
	bufferedImage = new BufferedImage( 
					  width,
					  height,
					  BufferedImage.TYPE_INT_ARGB
					   );

	// creates the "pen" to draw on bufferedimages
    	g2 = bufferedImage.createGraphics();
	// set the line's width : 100
    	g2.setStroke(new BasicStroke(1));
	// set background color : black
	g2.setBackground(Color.black);
	// set the pen's color : white
    	g2.setColor(Color.WHITE);


	// convert the new bufferedImage to be able to add it
	imgLabel = new JLabel(new ImageIcon(bufferedImage));
	// display the whole canvas with the last strokes
	repaint();

    }


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

	g2.clearRect( 0, 0, width, height);

    	g2.setColor(Color.WHITE);    	
    	
    	// set the origin on the down-left corner
    	g2.translate(0, height - 1);

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
	
	g2.setColor(Color.RED);

    	// drawing the y axis
    	g2.draw( new Line2D.Double(
    			0 ,
				0,
				0,
				height
				));

    	// adding the x axis
    	g2.draw( new Line2D.Double(
    			0 ,
				height -1,
				width,
				height -1
				));

	// creates the new label corresponding to the last bufferImage
	imgLabel = new JLabel(new ImageIcon(bufferedImage));
	// add the label to this panel
	add(imgLabel);

    	repaint();// calls paintComponent()

    }

	@Override
    protected void paintComponent(Graphics graph) {
    	// calling mother's method
    	super.paintComponent(graph);

       	// ( Image, x, y, theOberserver )
    	g2.drawImage(bufferedImage, 0, 0, this);
	
    }


    // Panel visual settings
    private int height;
    private int width;

    // Used to draw anything
    private BufferedImage bufferedImage;

    // Used to draw the sound
    private Graphics2D g2;

    // Memorizing the y1 point (c.f drawData() )
    private double prevPoint;

    // The bufferedimage is stored here before adding it to the JPanel
    private JLabel imgLabel;

}
