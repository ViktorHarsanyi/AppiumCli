package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TypedCommand {

    String typedCommand() default "";

    boolean runTypedCommand() default false;
}
