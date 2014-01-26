package com.youthclub.annotation;

import com.youthclub.authentication.Authenticator;
import com.youthclub.lookup.LookUp;
import com.youthclub.model.User;
import com.youthclub.model.support.RoleType;
import com.youthclub.resource.LookUpExtension;
import org.jboss.resteasy.spi.UnauthorizedException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.ForbiddenException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Frank
 */
@RolesAllowed
@Interceptor
public class RolesAllowedInterceptor implements Serializable {

    @AroundInvoke
    public Object invoke(InvocationContext invocationContext) throws Exception {
        final Authenticator authenticator = LookUpExtension.getResourceHolder().getAuthenticator();
        final User user = authenticator.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException();
        }

        RolesAllowed rolesAllowed = invocationContext.getMethod().getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null) {
            rolesAllowed = invocationContext.getMethod().getDeclaringClass().getAnnotation(RolesAllowed.class);
        }
        List<RoleType> roleTypesAllowed = rolesAllowed == null ? Collections.<RoleType>emptyList() : Arrays.asList(rolesAllowed.value());
        if (roleTypesAllowed.contains(RoleType.ALL)) {
            return invocationContext.proceed();
        }

        List<RoleType> userRoleTypes = LookUp.getEntityManager()
                .createNamedQuery("UserRole.roleTypeWithUser", RoleType.class)
                .setParameter("user", user)
                .getResultList();
        for (RoleType userRoleType : userRoleTypes) {
            for (RoleType roleTypeAllowed : roleTypesAllowed) {
                if (userRoleType == roleTypeAllowed) {
                    return invocationContext.proceed();
                }
            }
        }
        throw new ForbiddenException();
    }
}
