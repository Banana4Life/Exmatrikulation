package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.object.DiceObject;

/**
 * The DiceSystem updates all the dices.
 * It uses the Family {Dice, Render}
 *
 * @author Jonas Dann
 */
public class DiceSystem extends IteratingSystem {
    private ComponentMapper<Dice> dices;
    private ComponentMapper<Render> render;

    /**
     * The constructor gets the ComponentMapper for Dice and Render.
     */
    public DiceSystem() {
        super(Family.all(Dice.class, Render.class).get());

        this.dices = ComponentMapper.getFor(Dice.class);
        this.render = ComponentMapper.getFor(Render.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        dices.get(entity).update();
        ((DiceObject) render.get(entity).getObject()).setCount(dices.get(entity).getCount());
    }
}
