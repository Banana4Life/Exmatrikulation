package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

/**
 * This resource bag holds the sounds that will be played in the game.
 *
 * @author Phillip Schichtel
 */
public class Sounds extends ResourceBag<Sound> {

    public Sound cardflip;
    public Sound dice;
    public Sound homebutton;
    public Sound decapitation;

    @Override
    protected Sound load(FileRef fileRef, Field field) {
        return Gdx.audio.newSound(Gdx.files.internal(fieldToFileRef(field, fileRef).getPath() + ".wav"));
    }
}
