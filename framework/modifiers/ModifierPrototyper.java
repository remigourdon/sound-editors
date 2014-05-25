package framework.modifiers;

import java.util.ArrayList;

import framework.modifiers.Modifier;

/**
 * Provides an array of modifiers' prototypes.
 *
 * New custom modifiers can be added.
 */
public class ModifierPrototyper {
    /**
     * Add a new modifier to the list of prototypes.
     * @param m the new modifier to be added
     */
    public static void addModifier(Modifier m) {
        prototypes.add(m);
    }

    /**
     * Get the array of prototypes.
     * @return the array of prototypes
     */
    public static Modifier[] getPrototypes() {
        return prototypes.toArray(new Modifier[0]);
    }

    private static ArrayList<Modifier> prototypes = new ArrayList<Modifier>();
}