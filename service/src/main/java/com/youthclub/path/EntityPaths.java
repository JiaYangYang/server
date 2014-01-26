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
    protected Class<T> entityClass;

    public static final String ID = "id";
    public static final String BY_ID = "/{id}";

    public static final String FIELD = "field";
    public static final String FIELD_NAME = "/{field}";

    protected abstract Class<T> getEntityClass() ;

    public Response get() {
        final EntityManager entityManager = LookUp.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return Response.ok(entityManager.createQuery(cq).getResultList()).build();
    }

    public Response post(final T that) {
        that.setId(0);
        LookUp.getEntityManager().persist(that);
        return Response.status(Response.Status.CREATED).entity(that.getId()).build();
    }

    public Response get(@PathParam(ID) final String id) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(
                LookUp.getEntityManager().find(entityClass, entityId)
        ).build();
    }

    public Response put(@PathParam(ID) final String id, final T that) {
        final int entityId;
        try {
            entityId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final EntityManager entityManager = LookUp.getEntityManager();
        if (entityManager.find(entityClass, entityId, LockModeType.PESSIMISTIC_WRITE) != null) {
            entityManager.merge(that);
            entityManager.flush();
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

        final EntityManager entityManager = LookUp.getEntityManager();
        final T entity = entityManager.find(entityClass, entityId, LockModeType.PESSIMISTIC_WRITE);
        if (entity != null) {
            entityManager.remove(entity);
            entityManager.flush();
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
        final EntityBase entity = LookUp.getEntityManager().find(entityClass, entityId);
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
        final EntityManager entityManager = LookUp.getEntityManager();
        final EntityBase entity = entityManager.find(entityClass, entityId, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        setFieldValue(entity, fieldName, that);
        entityManager.flush();

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
