package com.youthclub.view;

import com.youthclub.serializer.ViewSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Frank
 */
@JsonSerialize(using = ViewBase.Serializer.class)
public class ViewBase {
    public static class Serializer extends ViewSerializer<ViewBase> {
    }
}
