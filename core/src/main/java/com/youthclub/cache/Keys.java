package com.youthclub.cache;

import com.youthclub.view.CoordinatesView;
import org.infinispan.tree.Fqn;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Keys {

    public static Fqn from(final CoordinatesView that) {
        return Fqn.fromElements(that.toString());
    }

    public static Fqn from(final Date startDate, final Date endDate) {
        final SimpleDateFormat format = new SimpleDateFormat(CacheConstants.FORMAT);
        final StringBuilder builder = new StringBuilder();
        builder.append(format.format(startDate));
        if (endDate != null) {
            builder.append("-")
                    .append(format.format(endDate));
        }
        return Fqn.fromElements(builder.toString());
    }

    public static Fqn from(final Date date) {
        return from(date, null);
    }
}
