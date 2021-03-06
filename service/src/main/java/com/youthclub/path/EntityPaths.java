package com.youthclub.path;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.EntityBase;
import com.youthclub.support.PathUtil;

import javax.persistence.EntityManager;
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

    protected abstract Class<T> getEntityClass();

    protected T getById(final int id) {
        return LookUp.getEntityManager().find(getEntityClass(), id);
    }

    protected T getById(final String id) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return null;
        }
        return getById(entityId);
    }

    public Response get() {
        final EntityManager entityManager = LookUp.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return Response.ok(entityManager.createQuery(cq).getResultList()).build();
    }

    public Response post(final T that) {
        that.setId(0);
        LookUp.getEntityManager().persist(that);
        return Response.status(Response.Status.CREATED).entity(that.getId()).build();
    }

    public Response get(@PathParam(ID) final String id) {
        return Response.ok(getById(id)).build();
    }

    public Response put(@PathParam(ID) final String id, final T that) {
        final T entity = getById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final EntityManager entityManager = LookUp.getEntityManager();
        entityManager.lock(entity, LockModeType.PESSIMISTIC_WRITE);
        entityManager.merge(that);
        entityManager.flush();
        return Response.ok(that.getId()).build();
    }

    public Response delete(@PathParam(ID) final String id) {
        final T that = getById(id);
        if (that == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final EntityManager entityManager = LookUp.getEntityManager();
        entityManager.lock(that, LockModeType.PESSIMISTIC_WRITE);
        entityManager.remove(that);
        entityManager.flush();
        return Response.ok(that.getId()).build();
    }

    public Response getField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName) {
        final T that = getById(id);
        if (that == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(getFieldValue(that, fieldName)).build();
    }

    public Response setField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName, final T that) {
        final T entity = getById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final EntityManager entityManager = LookUp.getEntityManager();
        entityManager.lock(entity, LockModeType.PESSIMISTIC_WRITE);
        setFieldValue(entity, fieldName, that);
        entityManager.flush();
        return Response.ok(entity.getId()).build();
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
