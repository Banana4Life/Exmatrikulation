package de.cubeisland.games.dhbw.util.modelobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class ModelObject3D extends ModelObject {
    private ModelInstance instance;

    public ModelObject3D() {
        ObjLoader ldr = new ObjLoader();
        com.badlogic.gdx.graphics.g3d.Model model = ldr.loadModel(Gdx.files.internal("models/d20.obj"));
        instance = new ModelInstance(model);
        instance.transform.setToTranslation(0, 0, -50);
    }

    @Override
    public ModelObject3D setPosition(Vector3 position) {
        Quaternion rotation = instance.transform.getRotation(new Quaternion());
        instance.transform.setToTranslation(position);
        instance.transform.rotate(rotation);
        return this;
    }

    @Override
    public ModelObject3D setRotation(Quaternion rotation) {
        Vector3 position = instance.transform.getTranslation(new Vector3());
        instance.transform.setToTranslation(position);
        instance.transform.rotate(rotation);
        return this;
    }

    @Override
    public Vector3 getPosition() {
        return instance.transform.getTranslation(new Vector3());
    }

    @Override
    public boolean isClickOnModel(Camera camera, float screenX, float screenY) {
        return false;
    }

    public ModelInstance getInstance() {
        return instance;
    }
}
