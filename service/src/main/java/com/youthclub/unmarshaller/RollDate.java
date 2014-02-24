package com.youthclub.unmarshaller;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Sets the default date to calculated by taking the current date and
 * rolling the calendar as annotated.
 *
 * By default the value will be the current date.
 *
 * TODO This doesn't work correctly as if there isn't a query param it won't get called.
 * i.e. It will work on     ?date=&that=something
 * but not on               ?that=something
 */
@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(RollDateUnmarshaller.class)
public @interface RollDate {
    Roll[] value() default {};

    public @interface Roll {
        int field();
        int value();
    }
}
