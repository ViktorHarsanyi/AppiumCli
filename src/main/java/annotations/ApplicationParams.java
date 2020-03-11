package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationParams {

    String pathToApp();
    String appPackage();

    /*iOS capabilities
    * defaults to null value
    * in case of Android only
    * represented by empty String
     */
    String bundleId() default "";
    String xcodeOrgId() default "";
    String xcodeSigningId() default "";
    String updatedWDABundleId() default "";
}
