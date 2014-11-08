package de.cubeisland.games.dhbw.util;

import de.cubeisland.engine.reflect.codec.ConverterManager;
import de.cubeisland.engine.reflect.codec.converter.Converter;
import de.cubeisland.engine.reflect.exception.ConversionException;
import de.cubeisland.engine.reflect.node.Node;
import de.cubeisland.engine.reflect.node.StringNode;
import de.cubeisland.games.dhbw.entity.CardPrefab;

/**
 * Converts a String node to SubjectType and the other way around.
 *
 * @author Jonas Dann
 */
public class SubjectTypeConverter implements Converter<CardPrefab.SubjectType> {
    @Override
    public Node toNode(CardPrefab.SubjectType object, ConverterManager manager) throws ConversionException {
        return new StringNode(object.toString().toLowerCase());
    }

    @Override
    public CardPrefab.SubjectType fromNode(Node node, ConverterManager manager) throws ConversionException {
        return CardPrefab.SubjectType.valueOf(((StringNode) node).getValue().toUpperCase());
    }
}
