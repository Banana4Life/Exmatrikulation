package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Models extends ResourceBag<Model> {

    public Model card;
    public Model D20;

    private final ObjLoader ldr = new ObjLoader();

    @Override
    protected Model load(FileRef fileRef, Field field) {
        return ldr.loadModel(Gdx.files.internal(fieldToFileRef(field, fileRef).getPath() + ".obj"));
    }
}
