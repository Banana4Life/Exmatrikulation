package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import de.cubeisland.games.dhbw.resource.font.Font;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class Fonts extends ResourceBag<Font> {

    @Def(font = "neou/bold", size = 30)
    public Font defaultFont;

    @Def(font = "grundschrift/bold", size = 20)
    public Font cardFont;

    @Override
    protected Font load(FileRef basedir, Field field) {
        Def def = field.getAnnotation(Def.class);
        if (def == null) {
            throw new IllegalArgumentException("Missing @Def annotation!");
        }

        FileHandle file = Gdx.files.internal(basedir.child(def.font().replace('/', File.separatorChar) + ".ttf").getPath());
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(file);

        return new Font(gen, def.flipped(), def.size());
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Def {
        String font();
        int size();
        boolean flipped() default false;
    }
}
