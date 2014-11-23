package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.graphics.Texture;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

/**
 * A resourcebag that loads all the textures
 *
 * @author Jonas Dann
 */
public class Images extends ResourceBag<Texture> {
    public Texture background;
    public Texture cardback;
    public Texture cardfront;
    public Texture dice;
    public Texture logo;
    public Texture pause;

    public Texture splashscreen;
    public Texture semester1;
    public Texture semester2;
    public Texture semester3;
    public Texture semester4;
    public Texture semester5;
    public Texture semester6;
    public Texture losescreen;
    public Texture winscreenfreemode;
    public Texture winscreenstorymode;

    @Override
    protected Texture load(FileRef basedir, Field field) {
        final String id = field.getName();
        System.out.println(basedir.child(id + ".png").getPath());
        return new Texture(basedir.child(id + ".png").getPath());
    }
}
