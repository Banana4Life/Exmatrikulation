package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;

/**
 * This components attaches a text to the entity which can be rendered.
 *
 * @author Phillip Schichtel
 */
public class Text extends Component {
    private String text;

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String get() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the text to set
     * @return fluent interface
     */
    public Text set(String text) {
        this.text = text;
        return this;
    }
}
