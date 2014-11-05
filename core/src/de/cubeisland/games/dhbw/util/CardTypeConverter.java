package de.cubeisland.games.dhbw.util;

import de.cubeisland.engine.reflect.codec.ConverterManager;
import de.cubeisland.engine.reflect.codec.converter.Converter;
import de.cubeisland.engine.reflect.exception.ConversionException;
import de.cubeisland.engine.reflect.node.Node;
import de.cubeisland.engine.reflect.node.StringNode;
import de.cubeisland.games.dhbw.entity.CardPrefab;

/**
 * This converter converts between {@link de.cubeisland.games.dhbw.entity.CardPrefab.CardType} and
 * {@link de.cubeisland.engine.reflect.node.StringNode}.
 */
public class CardTypeConverter implements Converter<CardPrefab.CardType> {
	@Override
	public Node toNode(CardPrefab.CardType object, ConverterManager manager) throws ConversionException {
		return new StringNode(object.toString().toLowerCase());
	}

	@Override
	public CardPrefab.CardType fromNode(Node node, ConverterManager manager) throws ConversionException {
        return CardPrefab.CardType.valueOf(((StringNode)node).getValue().toUpperCase());
	}
}
