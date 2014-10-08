package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import de.cubeisland.engine.reflect.ReflectedYaml;

import java.util.ArrayList;

public class EntityPreFab extends ReflectedYaml {
    public ArrayList<Class<Component>> components;
}
