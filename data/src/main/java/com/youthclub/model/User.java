package com.youthclub.model;

import com.youthclub.annotation.AttributeAccess;
import com.youthclub.annotation.Internal;
import com.youthclub.annotation.support.AccessLevel;
import com.youthclub.annotation.support.Permission;
import com.youthclub.model.support.RoleType;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * @author frank
 */
@Entity
@Table(name = "user", schema = "public")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.login", query = "SELECT e FROM User e WHERE e.username=:username AND e.password=:password"),
        @NamedQuery(name = "User.findByIp", query = "SELECT e FROM User e WHERE e.bindIp=:ip")
})
public class User extends EntityBase<User> {
    private int id;
    private String username;
    private String password;
    private String bindIp;
    private Date disabled;
    private List<UserRole> userRoleList;
    private List<Session> sessionList;
    private List<UserLog> userLogList;

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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic(optional = false)
    @Column(name = "password")
    @Internal
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "bind_ip", unique = true)
    public String getBindIp() {
        return bindIp;
    }

    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    @Column(name = "disabled")
    @Temporal(TemporalType.TIMESTAMP)
    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    public Date getDisabled() {
        return disabled;
    }

    public void setDisabled(Date disabled) {
        this.disabled = disabled;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @OneToMany(mappedBy = "user")
    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @OneToMany(mappedBy = "user")
    public List<UserLog> getUserLogList() {
        return userLogList;
    }

    public void setUserLogList(List<UserLog> userLogList) {
        this.userLogList = userLogList;
    }
}
