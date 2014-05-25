package framework.views;

import javax.swing.*;
import java.util.*;
import java.util.Observer;

/**
 * Views implementing this interface get updated when the model's data changes.
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
