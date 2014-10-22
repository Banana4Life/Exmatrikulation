package de.cubeisland.games.dhbw.util.modelobject;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public abstract class ModelObject {
    public abstract ModelObject setPosition(Vector3 position);
    public abstract ModelObject setRotation(Quaternion rotation);
    public abstract Vector3 getPosition();
}
