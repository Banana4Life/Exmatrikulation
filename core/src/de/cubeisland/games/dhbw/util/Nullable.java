package de.cubeisland.games.dhbw.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * This annotation specifies that the annotated element might be null
 *
 * @author Phillip Schichtel
 */
@Retention(RetentionPolicy.CLASS)
@Target({METHOD, PARAMETER})
@Documented
public @interface Nullable {
}
