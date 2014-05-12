import javax.swing.*;
import java.util.*;
import java.util.Observer;

/**
 * According to the Observer design pattern, each view must extend this interface.
 *
 */
public interface View extends Observer {
    
    /**
     * 
     * @param Observable o    The observable sound model
     * @param Object     obj  The data array (the Sound)
     */
    void update(Observable o , Object dataChanged);

    
}
