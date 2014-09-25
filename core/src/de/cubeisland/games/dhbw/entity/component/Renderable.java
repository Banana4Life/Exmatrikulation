package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class Renderable extends Component {
    private Texture texture;

    public Renderable setTexture(Texture t) {
        this.texture = t;
        return this;
    }

    public Texture getTexture() {
        return this.texture;
    }
}
