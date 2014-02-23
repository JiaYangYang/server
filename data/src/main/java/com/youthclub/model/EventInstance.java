package com.youthclub.model;

import com.youthclub.annotation.AttributeAccess;
import com.youthclub.annotation.support.AccessLevel;
import com.youthclub.annotation.support.Permission;
import com.youthclub.model.support.RoleType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Entity
@Table(name = "event_instance", schema = "public")
@NamedQueries({
        @NamedQuery(name = "EventInstance.active", query = "SELECT e FROM EventInstance e WHERE e.disabled is NULL order by e.created desc"),
        @NamedQuery(name = "EventInstance.withUser", query = "SELECT e FROM EventInstance e WHERE e.user=:user order by e.created desc")
})
public class EventInstance extends EntityBase<EventInstance> {
    private int id;
    private String serializable;
    private Date created;
    private boolean verified;
    private Date disabled;
    private User user;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "serializable")
    public String getSerializable() {
        return serializable;
    }

    public void setSerializable(String serializable) {
        this.serializable = serializable;
    }

    @Basic
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "verified", nullable = false)
    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Basic
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
