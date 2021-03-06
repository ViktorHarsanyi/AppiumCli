package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControlNotation {
    boolean canGoToJira();
    String jiraKey() default "";
}