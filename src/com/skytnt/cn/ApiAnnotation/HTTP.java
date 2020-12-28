package com.skytnt.cn.ApiAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* compiled from: HTTP */
public @interface HTTP {
    boolean hasBody() default false;

    String method();

    String path() default "";
}
