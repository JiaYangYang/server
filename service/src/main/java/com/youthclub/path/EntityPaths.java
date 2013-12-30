package com.youthclub.path;

import com.youthclub.model.EntityBase;
import com.youthclub.persister.AbstractPersister;
import com.youthclub.support.PathUtil;

import javax.persistence.LockModeType;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public abstract class EntityPaths<T extends EntityBase<T>> extends ViewPaths<T> {

    private static final Logger log = Logger.getLogger(EntityPaths.class.getName());

    public static final String ID = "id";
    public static final String BY_ID = "/{id}";

    public static final String FIELD = "field";
    public static final String FIELD_NAME = "/{field}";

    protected abstract AbstractPersister<T> getPersister();

    public Response get() {
        return Response.ok(getPersister().createAllEntities()).build();
    }

    public Response post(final T that) {
        that.setId(0);
        getPersister().save(that);
        return Response.status(Response.Status.CREATED).entity(that.getId()).build();
    }

    public Response get(@PathParam(ID) final String id) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(getPersister().find(entityId)).build();
    }

    public Response get(@PathParam(ID) final String id, final T that) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final AbstractPersister<T> persister = getPersister();
        if (persister.find(entityId, LockModeType.PESSIMISTIC_WRITE) != null) {
            persister.save(that);
            persister.getEntityManager().flush();
        }

        return Response.ok().build();
    }

    public Response delete(@PathParam(ID) final String id) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final AbstractPersister<T> persister = getPersister();
        final T entity = persister.find(entityId, LockModeType.PESSIMISTIC_WRITE);
        if (entity != null) {
            persister.remove(entity);
            persister.getEntityManager().flush();
        }
        return Response.ok().build();
    }

    public Response getField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final EntityBase entity = getPersister().find(entityId);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(getFieldValue(entity, fieldName)).build();
    }

    public Response setField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName, final T that) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final EntityBase entity = getPersister().find(entityId, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        setFieldValue(entity, fieldName, that);
        getPersister().getEntityManager().flush();

        return Response.ok().build();
    }

    protected Object getFieldValue(final EntityBase that, final String fieldName) {
        final Method[] methods = that.getClass().getMethods();
        final String getter = PathUtil.isGetterNameFromJsonName(fieldName);
        final String booleanGetter = PathUtil.getterNameFromJsonName(fieldName);
        for (final Method method : methods) {
            if (method.getName().equals(getter) || method.getName().equals(booleanGetter)) {
                try {
                    return method.invoke(that, new Object[]{});
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE, null, e);
                } catch (InvocationTargetException e) {
                    log.log(Level.SEVERE, null, e);
                }
            }
        }
        return null;
    }

    protected boolean setFieldValue(final EntityBase entity, final String fieldName, final T that) {
        final Object value = getFieldValue(that, fieldName);
        final Method[] methods = entity.getClass().getMethods();
        final String setter = PathUtil.setterNameFromJsonName(fieldName);
        for (final Method method : methods) {
            if (method.getName().equals(setter)) {
                try {
                    method.invoke(entity, value);
                    return true;
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE, null, e);
                } catch (InvocationTargetException e) {
                    log.log(Level.SEVERE, null, e);
                }
            }
        }
        return false;
    }
}
