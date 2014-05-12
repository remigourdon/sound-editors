package framework.views;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.lang.Double;

/**
 * SignalPanel is used to display a waveform.
 * 
 */
public class SignalPanel extends JPanel {
    
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
    
    /**
     * Main method of this class.
     * Draws the waveform.
     * @param	Double	buffer	the data array to be plotted
     */
    public void drawData(Double[] buffer) {
    	// Data display settings
    	g2.setColor(Color.white);
    	g2.clearRect( 0, 0, width, height);
    	
    	// run through the buffer
    	for(int i = 0 ; i < buffer.length ; i++) {
    		double prevPoint = (double) buffer[i];
    		// draw( x1, y1, x2, y2)
    		// Previous point = (x1,y1) & Current point = (x2,y2)
    		g2.draw( new Line2D.Double(
    				buffer.length - i ,
    				currentPoint + height/2 ,
    				buffer.length - (i+1),
    				prevPoint + height/2
    				));
    		
    		System.out.println("Buffer = "+ buffer[i]);
    		currentPoint = prevPoint;
    		
    	}
    	repaint();
    }

    private int width;
    private int height;
    private double currentPoint;
    private Graphics2D g2;
    private BufferedImage bufferedImage;
    
}
