package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * The ControlSystem moves all Entities with a DestTransform Component towards the destination
 * It uses the Family {Transform, DestTransform}
 */
public class ControlSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<DestTransform> destTransforms;

    //needed for vector Interpolation
    private  Vector3 originalPosition;
    /**
     * The constructor gets the ComponentMapper for Transform and DestTransform
     */
    public ControlSystem() {
        super(Family.all(Transform.class, DestTransform.class).get());

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.destTransforms = ComponentMapper.getFor(DestTransform.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        DestTransform destTransform = destTransforms.get(entity);

        boolean movmentFinished=false;
        //TODO maybe rang fixed
        if(!vectorsInRange(transform.getPosition(),destTransform.getPosition(),1)) {
            transform.setPosition(vectorInterpolation(destTransform.getPosition(),transform.getPosition(),0.1f));
            movmentFinished=false;

        }else {
            movmentFinished=true;
        }

        boolean rotationFinished=true;
//        if(!quaternionsInRange(transform.getRotation(),destTransform.getRotation(),0.1f)){
//            Quaternion test=transform.getRotation().add(scaleQuaternion(destTransform.getRotation(),0.1f));
//            transform.setRotation(transform.getRotation().add(scaleQuaternion(destTransform.getRotation(),0.1f)));
//            rotationFinished=false;
//       }else {
//            rotationFinished=true;
//        }

        transform.setRotation(destTransform.getRotation());
        if(movmentFinished&&rotationFinished){
            entity.remove(DestTransform.class);
        }

    }



    private Vector3 vectorInterpolation(Vector3 destPosition, Vector3 orgPosition,float t){
        float x = orgPosition.x;
        float y = orgPosition.y;
        float z = orgPosition.z;
        x=x+ t*(destPosition.x-orgPosition.x);
        y=y+ t*(destPosition.y-orgPosition.y);
        z=z+ t*(destPosition.z-orgPosition.z);
        return new Vector3(x,y,z);
    }

    private Quaternion  scaleQuaternion(Quaternion quaternion, float scale){
        quaternion.x*=scale;
        quaternion.y*=scale;
        quaternion.z*=scale;
        quaternion.w*=scale;
        return quaternion;
    }

    private boolean quaternionsInRange(Quaternion quaternion1,Quaternion quaternion2,float range) {
        if(Math.abs(quaternion1.w-quaternion2.w)<range)  {
            return true;
        }
        return false;
    }

    private boolean vectorsInRange(Vector3 vector1,Vector3 vector2, int range){
        if(Math.abs(vector1.x-vector2.x)<range && Math.abs(vector1.y-vector2.y)<range &&Math.abs(vector1.z-vector2.z)<range)  {
            return true;
        }
        return false;
    }
}
