package com.youthclub.model;

import com.youthclub.deserializer.ViewDeserializer;
import com.youthclub.model.support.RoleType;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Frank
 */
@Entity
@Table(name = "user_role", schema = "public")
@NamedQueries({
        @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
        @NamedQuery(name = "UserRole.roleTypeWithUser", query = "SELECT e.roleType FROM UserRole e WHERE e.user=:user")
})
@JsonDeserialize(using = UserRole.Deserializer.class)
public class UserRole extends EntityBase<UserRole> {
    private int id;
    private RoleType roleType;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "role_type")
    @Enumerated(EnumType.ORDINAL)
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Deserializer extends ViewDeserializer<UserRole> {

        protected Deserializer() {
            super(UserRole.class);
        }
    }
}
