package com.youthclub.unmarshaller;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.EntityBase;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

import java.lang.annotation.Annotation;

public class FindUnmarshaller<T extends EntityBase<T>> implements StringParameterUnmarshaller<T> {
    private Class<? extends EntityBase> clazz;

    public void setAnnotations(final Annotation[] annotations) {
        final Find find = FindAnnotation.findAnnotation(annotations, Find.class);
        clazz = find.value();
    }

    public T fromString(final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        final T that = (T) LookUp.getEntityManager().find(clazz, Integer.parseInt(value));
        if (that == null) {
            return null;
        }
        return that;
    }
}
