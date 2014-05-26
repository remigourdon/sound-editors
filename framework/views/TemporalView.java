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
 * TemporalView provides a View corresponding to the Observer Pattern.
 * It draws a temporal representation of the model's data.
 *
 */
public class TemporalView extends JPanel implements View {


    /**
     * Build a TemporalView.
     * @param int w Width of the panel.
     * @param int h	Height of the panel.
     * @param Sound s The sound to be displayed.
     */
    public TemporalView(int w, int h){

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
	System.out.println("Constructor");
    }

     /**
 
     * 
     * The update method is called when a change occurs in the model (theSound).
     * @param Observable o   		The sound model.
     * @param Object     dataChanged 	Is a boolean indicating if the model has been modified.
     * @Override
     */
    public void update(Observable o, Object dataChanged) {

	// Retrieving the sound model
	Sound s = (Sound) o;

	// Retrieving the sound buffer
	Double[] data = s.getData();	
	System.out.println("update");
	if( (boolean) dataChanged) {
	    // plot the new waveform
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
	
	// erase previous strokes
	g2.clearRect( 0, 0, width, height);

	g2.setColor(Color.WHITE);
	//g2.scale(width,height);
    	// Go through the whole data and connects the dots
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

	g2.setColor(Color.RED);

    	// adding the x axis
    	g2.draw( new Line2D.Double(
    			0 ,
			height/2,
			width,
			height/2
				  ));

	//take out the old one from this panel
	remove(imgLabel);
	// creates the new label corresponding to the last bufferImage
	imgLabel = new JLabel(new ImageIcon(bufferedImage));
	// add the label to this panel
	add(imgLabel);

	// calls paintComponent()
    	repaint();
	System.out.println("DrawData");
    }

    @Override
    protected void paintComponent(Graphics g) {
    	// calling mother's method
    	super.paintComponent(g);
	//Graphics2D g2 = (Graphics2D)g;

	
	// draws on the bufferedImage
	g2.drawImage(bufferedImage, 0, 0, this);

	System.out.println("paintComponent");
    }

    // Panel visual settings
    private int height;
    private int width;

    // The "canvas"
    private BufferedImage bufferedImage;

    //The "pen"
    private Graphics2D g2;

    // Memorizing the y1 point (c.f drawData() )
    private double currentPoint;

    // The bufferedimage is stored here before adding it to the JPanel
    private JLabel imgLabel;
}
