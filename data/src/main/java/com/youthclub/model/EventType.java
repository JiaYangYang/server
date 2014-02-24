package com.youthclub.model;

import com.youthclub.annotation.AttributeAccess;
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
@Table(name = "event_type", schema = "public")
@NamedQueries({
        @NamedQuery(name = "EventType.all", query = "SELECT e FROM EventType e"),
        @NamedQuery(name = "EventType.active", query = "SELECT e FROM EventType e WHERE e.disabled is null")
})
public class EventType extends EntityBase<EventType> {

    private int id;
    private String name;
    private Date created;
    private Date disabled;
    private List<Event> eventCollection;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    @Column(name = "disabled")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDisabled() {
        return disabled;
    }

    public void setDisabled(Date disabled) {
        this.disabled = disabled;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventType")
    public List<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(List<Event> eventCollection) {
        this.eventCollection = eventCollection;
    }
}
