package com.youthclub.annotation.support;

import com.youthclub.model.support.RoleType;

/**
 * @author Frank
 */
public @interface Permission {
    RoleType roleType() default RoleType.GUEST;

    AccessLevel[] accessLevel() default {AccessLevel.NONE};
}
