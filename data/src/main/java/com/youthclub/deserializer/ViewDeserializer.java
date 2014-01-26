package com.youthclub.deserializer;

import com.youthclub.annotation.support.AccessLevel;
import com.youthclub.model.EntityBase;
import com.youthclub.model.support.RestfulEnum;
import com.youthclub.permission.ViewPermissionChecker;
import com.youthclub.support.PathUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public class ViewDeserializer<T> extends StdDeserializer<T> {

    private static final AccessLevel[] acceptedAccessLevels = {AccessLevel.WRITE, AccessLevel.CREATE, AccessLevel.ALL};

    private final Class<T> viewClass;

    protected ViewDeserializer(final Class<T> viewClass) {
        super(viewClass);
        this.viewClass = viewClass;
    }

    protected boolean checkPermission(Method method) {
        final Set<AccessLevel> accessLevels = ViewPermissionChecker.getAccessPermission(method);
        for (final AccessLevel accessLevel : acceptedAccessLevels) {
            if (accessLevels.contains(accessLevel)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        T that;
        Class<T> thatClass = viewClass;
        try {
            final JsonNode tree = parser.readValueAsTree();
            that = viewClass.newInstance();

            final Iterator<Map.Entry<String, JsonNode>> iterator = tree.getFields();
            while (iterator.hasNext()) {
                final Map.Entry<String, JsonNode> entry = iterator.next();
                final String setter = PathUtil.setterNameFromJsonName(entry.getKey());
                for (final Method method : thatClass.getMethods()) {
                    if (method.getName().equals(setter)) {
                        final JsonNode node = entry.getValue();
                        if (method.getParameterTypes().length != 1) {
                            continue;
                        }
                        if (!checkPermission(method)) {
                            continue;
                        }
                        Type type;
                        if (method.getGenericParameterTypes().length != 0) {
                            type = method.getGenericParameterTypes()[0];
                        } else {
                            type = Object.class;
                        }
                        final Object value = instanceOf(method.getParameterTypes()[0], type, node);
                        method.invoke(that, value);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return that;
    }

    private Object instanceOf(final Class<?> clazz, final Type type, final JsonNode node) throws Exception {
        if (node.isNull()) {
            return null;
        }
        if (clazz.isEnum() && RestfulEnum.class.isAssignableFrom(clazz)) {
            RestfulEnum[] values = ((Class<? extends RestfulEnum>) clazz).getEnumConstants();
            String value = node.asText();
            for (RestfulEnum e : values) {
                if (e.getLabel().equals(value)) {
                    return e;
                }
            }
        }

        if (Boolean.class.isAssignableFrom(clazz)
                || boolean.class.isAssignableFrom(clazz)) {
            return node.asBoolean();
        }
        if (Short.class.isAssignableFrom(clazz)
                || short.class.isAssignableFrom(clazz)
                || Integer.class.isAssignableFrom(clazz)
                || int.class.isAssignableFrom(clazz)) {
            return node.asInt();
        }
        if (Long.class.isAssignableFrom(clazz)
                || long.class.isAssignableFrom(clazz)) {
            return node.asLong();
        }
        if (Float.class.isAssignableFrom(clazz)
                || float.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz)
                || double.class.isAssignableFrom(clazz)) {
            return node.asDouble();
        }

        if (String.class.isAssignableFrom(clazz)) {
            return StringEscapeUtils.unescapeJava(node.asText());
        }

        if (Date.class.isAssignableFrom(clazz)) {
            return new Date(node.asLong());
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return resolveCollection(clazz, type, node);
        }

        if (EntityBase.class.isAssignableFrom(clazz)) {
            return resolveEntity(clazz, node);
        }

        try {
            return clazz.newInstance();
        } catch (final InstantiationException e) {
            throw new IllegalStateException("Could not instantiate " + clazz.getCanonicalName(), e);
        }
    }

    public EntityBase resolveEntity(final Class<?> clazz, final JsonNode node) throws Exception {
        if (node == null) {
            return null;
        }
        final int id;
        if (node.isObject()) {
            if (node.size() > 1) {
                return null;
            }
            final JsonNode value = node.get("$ref");
            if (value == null) {
                return null;
            }
            id = value.asInt();
        } else {
            id = node.asInt();
        }
        if (id == 0) {
            return null;
        }
        final EntityBase that = (EntityBase) clazz.newInstance();
        that.setId(id);

        return that;
    }

    public Collection<?> resolveCollection(final Class<?> clazz, final Type type, final JsonNode node) throws Exception {
        Collection<Object> collection;
        if (List.class.isAssignableFrom(clazz)) {
            collection = new ArrayList<>();
        } else {
            collection = new HashSet<>();
        }

        final Iterator<JsonNode> iterator = node.getElements();
        while (iterator.hasNext()) {
            final JsonNode entry = iterator.next();
            ParameterizedType genericType = (ParameterizedType) type;
            Class genericClazz = (Class) genericType.getActualTypeArguments()[0];
            final Object value = instanceOf(genericClazz, genericClazz, entry);
            collection.add(value);
        }
        return collection;
    }
}

