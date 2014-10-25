package de.cubeisland.games.dhbw.util;

public interface Factory<Out, In extends Prefab> {
    Out create(In in);
}
