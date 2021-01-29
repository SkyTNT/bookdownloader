package moe.fab.sfacgreader.ApiAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* compiled from: Part */
public @interface PART {
    String encoding() default "binary";

    String value() default "";
}
