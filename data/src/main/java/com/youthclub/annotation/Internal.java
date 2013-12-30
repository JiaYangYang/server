package com.youthclub.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Frank
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Internal {
}
