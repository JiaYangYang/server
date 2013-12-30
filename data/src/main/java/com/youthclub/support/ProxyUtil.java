package com.youthclub.support;

import org.hibernate.proxy.HibernateProxy;

/**
 * @author Brent Douglas <brent.n.douglas@gmail.com>
 */
public class ProxyUtil {

    public static <T> Class<T> getClass(final T that) {
        if (that instanceof HibernateProxy) {
            return ((HibernateProxy) that).getHibernateLazyInitializer().getPersistentClass();
        } else {
            return (Class<T>) that.getClass();
        }
    }

    public static <T> T getValue(final T that) {
        if (that instanceof HibernateProxy) {
            return (T) ((HibernateProxy) that).getHibernateLazyInitializer().getImplementation();
        } else {
            return that;
        }
    }
}
