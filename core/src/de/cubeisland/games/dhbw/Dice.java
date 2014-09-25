package de.cubeisland.games.dhbw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Dice {
    private Board board;

    private Vector3 speed;
    private ModelInstance instance;

    public Dice(Board board) {
        this.board = board;

        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);
        instance.transform.setToTranslation(0, 0, -50);
        speed = new Vector3(10f, 0, 0);
    }

    public void render (float delta) {
        Quaternion rot = instance.transform.getRotation(new Quaternion());
        Vector3 trans = instance.transform.getTranslation(new Vector3());
        instance.transform.setToTranslation(trans);
        instance.transform.translate(speed.cpy().scl(delta));
        instance.transform.rotate(rot);
        instance.transform.rotate(new Vector3(-speed.y / speed.x, 1, 0), speed.len() * delta * 10f);

        board.getGame().getModelBatch().begin(board.getGame().getCamera());
        board.getGame().getModelBatch().render(instance, board.getGame().getEnvironment());
        board.getGame().getModelBatch().end();
    }
}
