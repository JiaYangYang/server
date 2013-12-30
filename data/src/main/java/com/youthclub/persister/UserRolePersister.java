package com.youthclub.persister;

import com.youthclub.model.User;
import com.youthclub.model.UserRole;
import com.youthclub.model.support.RoleType;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Frank
 */
@Stateless
@LocalBean
public class UserRolePersister extends AbstractPersister<UserRole> {

    public UserRolePersister() {
        super(UserRole.class);
    }

    public List<RoleType> roleTypes(User user) {
        return entityManager
                .createNamedQuery("UserRole.roleTypeWithUser", RoleType.class)
                .setParameter("user", user)
                .getResultList();
    }
}
