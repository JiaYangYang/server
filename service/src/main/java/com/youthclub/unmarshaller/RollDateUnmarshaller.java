package com.youthclub.unmarshaller;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Date;

public class RollDateUnmarshaller implements StringParameterUnmarshaller<Date> {
    private RollDate rollDate;

    public void setAnnotations(final Annotation[] annotations) {
        rollDate = FindAnnotation.findAnnotation(annotations, RollDate.class);
    }

    public Date fromString(final String value) {
        if (value == null || "".equals(value)) {
            final Calendar calendar = Calendar.getInstance();
            for (final RollDate.Roll roll : this.rollDate.value()) {
                calendar.roll(roll.field(), roll.value());
            }
            return calendar.getTime();
        }
        return new Date(Long.parseLong(value));
    }
}
