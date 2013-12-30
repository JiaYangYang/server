package com.youthclub.util;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Frank
 */
public class ObjectCloneUtil {

    private ObjectCloneUtil() {
    }

    public static <T> List<T> newInstanceCollection(List sourceList, Class<T> targetClass) {
        if (sourceList == null || targetClass == null) {
            return Collections.emptyList();
        }
        List<T> ret = new ArrayList<T>();
        for (Object c : sourceList) {
            T target = newInstance(c, targetClass);
            if (target != null) {
                ret.add(target);
            }
        }
        return ret;
    }

    public static <T> T newInstance(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        T target;
        try {
            target = targetClass.newInstance();
        } catch (Exception ex) {
            Logger.getLogger(ObjectCloneUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        copyObject(source, target);
        return target;
    }

    public static void copyObject(final Object source, final Object target) {
        if (source == null || target == null) {
            return;
        }
        Class<?> tClass = target.getClass();
        Field[] fields = tClass.getDeclaredFields();
        Class<?> sClass = source.getClass();
        for (final Field field : fields) {
            String name = field.getName();
            try {
                final Field f = sClass.getDeclaredField(name);
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        try {
                            boolean accessible = f.isAccessible();
                            if (!accessible) {
                                f.setAccessible(true);
                            }
                            Object v = f.get(source);
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            field.set(target, v);
                            f.setAccessible(accessible);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        return null;
                    }
                });
            } catch (NoSuchFieldException ex) {
                //Target has but source doesn't have
                continue;
            }
        }
    }
}
