package com.youthclub.unmarshaller;

import com.youthclub.model.EntityBase;
import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(FindUnmarshaller.class)
public @interface Find {

    /**
     * @return The class of the value to inject.
     */
    Class<? extends EntityBase> value();
}
