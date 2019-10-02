package lesson15.framework.annotations;

import java.lang.annotation.*;

/**
 * In this case, numbering is required!
 * Number the classes in the initialization order
 * (from the database to the user interface)
 *
 * @since   late at night
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    int value() default 0;
}
