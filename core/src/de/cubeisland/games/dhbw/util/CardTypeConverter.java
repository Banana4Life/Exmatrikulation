package de.cubeisland.games.dhbw.util;

import de.cubeisland.engine.reflect.codec.ConverterManager;
import de.cubeisland.engine.reflect.codec.converter.Converter;
import de.cubeisland.engine.reflect.exception.ConversionException;
import de.cubeisland.engine.reflect.node.Node;
import de.cubeisland.engine.reflect.node.StringNode;

public class CardTypeConverter implements Converter<Class<?>> {

	public enum CardType {
		MENU, EVENT, ITEM
	}

	// TODO
	@Override
	public Node toNode(Class<?> object, ConverterManager manager) throws ConversionException {
		return new StringNode(object.getName());
	}

	@Override
	public Class<?> fromNode(Node node, ConverterManager manager) throws ConversionException {
		try {
			return Class.forName(node.asText());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("CardTypeConverter found no valid CardType!", e);
		}
	}
}
