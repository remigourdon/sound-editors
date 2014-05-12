import javax.swing.*;
import java.util.*;
import java.util.Observer;

/**
 * According to the Observer pattern, each view must implement this interface.
 *
 */
public interface View implements Observer {
    
    /**
     * 
     * @param Observable o    The observable sound model
     * @param Object     obj  The data array (the Sound)
     */
    void update(Observable o , Object obj);

    
}
