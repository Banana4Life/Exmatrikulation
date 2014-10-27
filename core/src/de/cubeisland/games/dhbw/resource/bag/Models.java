package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.UBJsonReader;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Models extends ResourceBag<Model> {

    public Model card;
    public Model d20;
    public Model lectureroom;

    private final G3dModelLoader ldr = new G3dModelLoader(new UBJsonReader());

    @Override
    protected Model load(FileRef fileRef, Field field) {
        return ldr.loadModel(Gdx.files.internal(fieldToFileRef(field, fileRef).getPath() + ".g3db"));
    }
}
