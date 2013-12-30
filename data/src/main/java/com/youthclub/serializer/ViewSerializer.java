package com.youthclub.serializer;

import com.youthclub.annotation.Internal;
import com.youthclub.annotation.support.AccessLevel;
import gnu.trove.map.TMap;
import com.youthclub.model.EntityBase;
import com.youthclub.model.support.RestfulEnum;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jboss.logging.Logger;
import com.youthclub.permission.ViewPermissionChecker;
import com.youthclub.support.MapEntry;
import com.youthclub.support.PathUtil;
import com.youthclub.support.ProxyUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewSerializer<T> extends JsonSerializer<T> {

    private static final Logger log = Logger.getLogger(ViewSerializer.class);
    private static final AccessLevel[] acceptedAccessLevels = {AccessLevel.READ, AccessLevel.ALL};

    protected Class<?> getSerializationClass(final T that) {
        return ProxyUtil.getClass(that);
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
    public void serialize(final T that, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
        final Class<?> cls = getSerializationClass(that);
        final List<Method> use = new ArrayList<>();
        Method[] allMethods = cls.getMethods();

        for (final Method any : allMethods) {
            boolean present = any.isAnnotationPresent(Internal.class);
            if (present) {
                continue;
            }

            final String name = any.getName();
            if (name.contains("$javassist") || !name.startsWith("get") && !name.startsWith("is")) {
                continue;
            }
            if (any.isBridge()) { //This is an inherited version of the method we want
                continue;
            }
            if (any.getReturnType().equals(Class.class) || any.getReturnType().equals(Void.class)) {
                continue;
            }
            if (Collection.class.isAssignableFrom(any.getReturnType())) {
                try {
                    ParameterizedType genericReturnType = (ParameterizedType) any.getGenericReturnType();
                    Class clazz = (Class) genericReturnType.getActualTypeArguments()[0];
                    if (EntityBase.class.isAssignableFrom(clazz)) {
                        continue;
                    }
                } catch (Exception e) {
                    log.log(Logger.Level.ERROR, e.getMessage(), e);
                    continue;
                }
            }
            if (use.contains(any)) {
                continue;
            }
            if (!checkPermission(any)) {
                continue;
            }

            use.add(any);
        }

        Collections.sort(use, new Comparator<Method>() {
            @Override
            public int compare(Method m1, Method m2) {
                return m1.getName().compareTo(m2.getName());
            }
        });

        generator.writeStartObject();
        for (Method method : use) {
            final String name = PathUtil.jsonNameFromGetterName(method.getName());

            Object value = null;
            try {
                value = method.invoke(that, new Object[]{});
            } catch (IllegalAccessException e) {
                log.log(Logger.Level.ERROR, e.getMessage(), e);
                continue;
            } catch (InvocationTargetException e) {
                log.log(Logger.Level.ERROR, e.getMessage(), e);
                continue;
            }

            if (value == null) {
                generator.writeFieldName(name);
                generator.writeNull();
            } else {
                Class<?> returnType = method.getReturnType();
                if (Integer.class.isAssignableFrom(returnType)
                        || Float.class.isAssignableFrom(returnType)
                        || Double.class.isAssignableFrom(returnType)
                        || Short.class.isAssignableFrom(returnType)
                        || Long.class.isAssignableFrom(returnType)
                        || long.class.isAssignableFrom(returnType)
                        || short.class.isAssignableFrom(returnType)
                        || int.class.isAssignableFrom(returnType)
                        || float.class.isAssignableFrom(returnType)
                        || double.class.isAssignableFrom(returnType)
                        || BigDecimal.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeNumber((int) value);
                } else if (Boolean.class.isAssignableFrom(returnType)
                        || boolean.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeBoolean((boolean) value);
                } else if (String.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    try {
                        generator.writeString(StringEscapeUtils.escapeJava((String) value));
                    } catch (UnsupportedEncodingException ex) {
                        generator.writeString((String) value);
                    }
                } else if (RestfulEnum.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeString(((RestfulEnum) value).getLabel());
                } else if (Date.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeNumber(((Date) value).getTime());
                } else if (Collection.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeObject(value);
                } else if (Map.class.isAssignableFrom(returnType) || TMap.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    Map map = (Map) value;
                    generator.writeObject(convertMap(map));
                } else if (MapEntry.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeObject(value);
                } else if (EntityBase.class.isAssignableFrom(returnType)) {
                    generator.writeFieldName(name);
                    generator.writeStartObject();
                    generator.writeFieldName("$ref");
                    generator.writeNumber(((EntityBase) value).getId());
                    generator.writeEndObject();
                } else {
                    boolean present = returnType.isAnnotationPresent(JsonSerialize.class);
                    if (present) {
                        generator.writeFieldName(name);
                        generator.writeObject(value);
                    }
                }
            }
        }
        generator.writeEndObject();
    }

    private Collection convertMap(Map map) {
        Collection ret = new ArrayList();
        Set<Map.Entry> set = map.entrySet();
        Iterator<Map.Entry> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            MapEntry e = new MapEntry()
                    .setKey(entry.getKey())
                    .setValue(entry.getValue());
            ret.add(e);
        }
        return ret;
    }
}