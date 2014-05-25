package framework.views;

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
 *	TemporalView provides a View corresponding to the Observer Pattern (MVC ?!).
 *	It uses the SignalPanel class to draw a temporal representation of the model's buffer.
 *
 */
public class TemporalView extends JPanel implements View {


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
     * Main method of this class.
     * Draws the waveform.
     * @param	Double	buffer	the data array to be plotted
     */
    public void drawData(Double[] buffer) {

    	// convert Double[] to double[]
    	double[] output = new double[buffer.length];
    	for(int i = 0; i < buffer.length ; i++) {
    		output[i]=buffer[i].doubleValue();
    	}

    	// Data display settings
    	g2.setColor(Color.WHITE);
    	g2.clearRect( 0, 0, width, height);
    	g.clearRect( 0, 0, width, height);
		// set the line's width
    	g2.setStroke(new BasicStroke(100));


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
    		currentPoint = prevPoint;

    	}
    	// adding the x axis
    	g.draw( new Line2D.Double(
    			0 ,
				height/2,
				width,
				height/2
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

	JLabel ImgLabel = new JLabel(new ImageIcon(bufferedImage));
    	add(ImgLabel);
    	getParent().add(this);

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

    	g = bufferedImage.createGraphics();
    }

    // Panel visual settings
    private int height;
    private int width;

    // Used to draw the sound
    private BufferedImage bufferedImage;
    public Graphics2D g2;

    public Graphics2D g;

    // Memorizing the y1 point (c.f drawData() )
    private double currentPoint;

}
