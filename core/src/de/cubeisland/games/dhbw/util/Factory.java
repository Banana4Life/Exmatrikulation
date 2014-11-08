package de.cubeisland.games.dhbw.util;

/**
 * This interface specifies a factory that takes some kind of prefab and produces an object from that prefab.
 *
 * @param <Out> the type of the object that will be produced
 * @param <In>  the type of the prefab
 * @author Phillip Schichtel
 */
public interface Factory<Out, In extends Prefab<Out>> {
    Out create(In in);
}
