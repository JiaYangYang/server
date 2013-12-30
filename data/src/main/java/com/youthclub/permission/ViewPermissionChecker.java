package com.youthclub.permission;

import com.youthclub.annotation.AttributeAccess;
import com.youthclub.annotation.support.AccessLevel;
import com.youthclub.annotation.support.Permission;
import com.youthclub.model.User;
import com.youthclub.model.support.RoleType;
import com.youthclub.persister.UserRolePersister;
import com.youthclub.resource.LookUpExtension;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Frank
 */
public class ViewPermissionChecker {

    public static Set<AccessLevel> getAccessPermission(Method method) {
        final boolean present = method.isAnnotationPresent(AttributeAccess.class);
        if (!present) {
            return Collections.singleton(AccessLevel.ALL);
        }
        final AttributeAccess attributeAccess = method.getAnnotation(AttributeAccess.class);
        final Permission[] permissions = attributeAccess.value();
        if (permissions == null) {
            return Collections.singleton(AccessLevel.ALL);
        }
        final Set<AccessLevel> accessLevels = new HashSet<>();
        final List<RoleType> roleTypes = getRoleTypes();
        for (Permission permission : permissions) {
            final RoleType roleType = permission.roleType();
            if (roleType == RoleType.ALL
                    || roleTypes.contains(RoleType.ALL)
                    || roleTypes.contains(roleType)) {
                final AccessLevel[] levels = permission.accessLevel();
                if (levels != null) {
                    for (AccessLevel level : levels) {
                        accessLevels.add(level);
                    }
                }
            }
        }
        return accessLevels;
    }

    public static List<RoleType> getRoleTypes() {
        final User user = LookUpExtension.getResourceHolder().getAuthenticator().getCurrentUser();
        if (user == null) {
            return Collections.<RoleType>emptyList();
        }
        final UserRolePersister userRolePersister = LookUpExtension.getPersister(UserRolePersister.class);
        return userRolePersister.roleTypes(user);
    }
}
