
package moe.fab.sfacgreader.ApiAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* compiled from: FieldMap */
public @interface FIELD_MAP {
    boolean encoded() default false;
}
