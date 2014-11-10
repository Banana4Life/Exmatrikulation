package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.component.PlayerChar;
import de.cubeisland.games.dhbw.entity.component.Text;

import static de.cubeisland.games.dhbw.entity.CardPrefab.SubjectType.*;

/**
 * todo javadoc
 */
public class StatusSystem extends IteratingSystem {

    private final ComponentMapper<PlayerChar> chars;
    private final ComponentMapper<Text> texts;

    public StatusSystem() {
        super(Family.all(Text.class, PlayerChar.class).get());

        this.chars = ComponentMapper.getFor(PlayerChar.class);
        this.texts = ComponentMapper.getFor(Text.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Text t = texts.get(entity);
        PlayerCharacter c = chars.get(entity).get();

        // DON'T LOOK AT THIS! GO AWAY...+
        final String text = "Skills:\n\n" +
                " " + BWL + ":            " + c.getBwl() + "\n" +
                " " + MATH + ":           " + c.getMath() + "\n" +
                " " + PROGRAMMING + ":         " + c.getProgramming() + "\n" +
                " " + SOFTSKILL + ":  " + c.getSoftSkills();

        t.set(text);
    }
}
