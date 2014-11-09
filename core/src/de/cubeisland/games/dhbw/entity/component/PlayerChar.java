package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import de.cubeisland.games.dhbw.character.PlayerCharacter;

/**
 * This component binds the character to an entity for system selection
 *
 * @author Phillip Schichtel
 */
public class PlayerChar extends Component {

    private PlayerCharacter character;

    public PlayerCharacter get() {
        return character;
    }

    public void set(PlayerCharacter character) {
        this.character = character;
    }
}
