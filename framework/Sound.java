package framework;

import java.util.ArrayList;
import framework.editors.SoundEditor;

/**
 * Represents a sound entity.
 *
 * A sound is represented basically by a frequency and a duration.
 * It can then be modified by adding different modifiers.
 * It is the core of the model in our MVC design pattern implementation.
 */
public abstract class Sound extends Observable {
    /**
     * Creates a Sound object.
     */
    public Sound() {

    }

    /**
     * Add a new Modifier to the list.
     * @param m the new Modifier to be added
     */
    public void addModifier(Modifier m) {

    }

    /**
     * Remove the specified Modifier from the list.
     * @param m the Modifier to be removed
     */
    public void removeModifier(Modifier m) {

    }

    /**
     * Instanciate a SoundEditor and hand back its reference.
     * @return the reference to the SoundEditor
     */
    public SoundEditor addEditor() {

    }

    private int                 frequency;  // Hertzs
    private double              duration;   // Milliseconds
    private byte[]              data;
    private ArrayList<Modifier> modifiers;
}