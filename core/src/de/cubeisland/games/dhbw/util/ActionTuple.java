package de.cubeisland.games.dhbw.util;

import de.cubeisland.engine.reflect.codec.ConverterManager;
import de.cubeisland.engine.reflect.codec.converter.Converter;
import de.cubeisland.engine.reflect.exception.ConversionException;
import de.cubeisland.engine.reflect.node.IntNode;
import de.cubeisland.engine.reflect.node.ListNode;
import de.cubeisland.engine.reflect.node.Node;
import de.cubeisland.engine.reflect.node.StringNode;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

import java.util.List;

/**
 * This class holds the type of an action and the parameter
 */
public class ActionTuple {
    private final CardAction actionType;
    private final Integer parameter;

    public ActionTuple(@NotNull CardAction actionType, @Nullable Integer parameter) {
        this.actionType = actionType;
        this.parameter = parameter;
    }

    @NotNull
    public CardAction getAction() {
        return actionType;
    }

    @Nullable
    public int getParameter() {
        return parameter == null ? 0 : parameter;
    }

    /**
     * Applies the action to the given character
     *
     * @param c the character
     */
    public void apply(@NotNull PlayerCharacter c) {
        getAction().apply(c, getParameter());
    }

    /**
     * This converter converts action tuples like [package.ClassName, 1] into {@link de.cubeisland.games.dhbw.util.ActionTuple} instances
     */
    public static class ActionConverter implements Converter<ActionTuple> {

        @Override
        public Node toNode(ActionTuple object, ConverterManager manager) throws ConversionException {
            ListNode tuple = ListNode.emptyList();
            tuple.addNode(StringNode.of(object.getAction().getClass().getName()));
            tuple.addNode(IntNode.wrapIntoNode(object.getParameter()));
            return tuple;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ActionTuple fromNode(Node node, ConverterManager manager) throws ConversionException {
            if (node instanceof ListNode) {
                List<Node> tuple = ((ListNode) node).getValue();
                if (tuple.isEmpty()) {
                    throw ConversionException.of(this, node, "Tuple may not be empty!");
                }

                Class type = manager.convertFromNode(tuple.get(0), Class.class);
                Integer parameter = null;
                if (tuple.size() > 1) {
                    parameter = manager.convertFromNode(tuple.get(1), Integer.class);
                }
                try {
                    return new ActionTuple((CardAction)type.newInstance(), parameter);
                } catch (ReflectiveOperationException e) {
                    throw ConversionException.of(this, node, "Failed to create instance!", e);
                }
            }
            throw ConversionException.of(this, node, "Not a list");
        }
    }
}
