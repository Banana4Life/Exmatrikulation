package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.RenderObject2D;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Text;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.resource.font.Font;

/**
 * An object that renders 2D text.
 */
public class TextObject implements RenderObject2D {

    private final Font font;
    private final Color color;
    private final ComponentMapper<Text> texts = ComponentMapper.getFor(Text.class);

    public TextObject(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    @Override
    public void render(DHBWGame game, Camera cam, Entity e, Transform transform) {
        Text t = texts.get(e);
        if (t == null) {
            return;
        }
        Vector3 pos = transform.getPosition();
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont bf = font.getBitmapFont();
        bf.setColor(color);
        batch.begin();
        bf.draw(batch, t.get(), pos.x, pos.y);
        batch.end();

    }

    @Override
    public boolean isWithin(Camera cam, float x, float y) {
        return false;
    }
}
