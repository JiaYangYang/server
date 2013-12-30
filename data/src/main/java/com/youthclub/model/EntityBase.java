package com.youthclub.model;

import com.youthclub.serializer.ViewSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Frank
 */
@JsonSerialize(using = EntityBase.Serializer.class)
public abstract class EntityBase<T extends EntityBase> implements java.io.Serializable, Comparable<T> {

    public abstract void setId(int id);

    public abstract int getId();

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId() + "]";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += getId();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !object.getClass().equals(getClass())) {
            return false;
        }
        T other = (T) object;
        if (getId() == other.getId() && getId() != 0) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(T t) {
        return getId() - t.getId();
    }

    public static class Serializer extends ViewSerializer<EntityBase> {
    }
}
