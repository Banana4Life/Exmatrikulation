package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.util.modelobject.ModelObject;

public class Model extends Component {
    private ModelObject modelObject;

    public void setModelObject(ModelObject modelObject) {
        this.modelObject = modelObject;
    }

    public ModelObject getModelObject() {
        return modelObject;
    }

    public Model setPosition(Vector3 position) {
        modelObject.setPosition(position);
        return this;
    }
    public Model setRotation(Quaternion rotation) {
        modelObject.setRotation(rotation);
        return this;
    }
    public Vector3 getPosition() {
        return modelObject.getPosition();
    }

    public boolean isClickOnModel(Camera camera, float screenX, float screenY) {
        return modelObject.isClickOnModel(camera, screenX, screenY);
    }
}
