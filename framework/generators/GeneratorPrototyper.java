package framework.generators;

import java.util.ArrayList;

import framework.generators.Generator;

/**
 * Provides an array of generators' prototypes.
 *
 * New custom generators can be added.
 */
public class GeneratorPrototyper {
    /**
     * Add a new generator to the list of prototypes.
     * @param g the new generator to be added
     */
    public static void addGenerator(Generator g) {
        prototypes.add(g);
    }

    /**
     * Get the array of prototypes.
     * @return the array of prototypes
     */
    public static Generator[] getPrototypes() {
        return prototypes.toArray(new Generator[0]);
    }

    private static ArrayList<Generator> prototypes = new ArrayList<Generator>();
}