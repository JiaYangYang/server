package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Collection;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template", schema = "public")
public class EventTemplate extends EntityBase<EventTemplate> {
    private int id;
    private String name;
    private Date disabled;
    private EventType eventType;
    private User user;
    private Collection<EventTemplateVersion> eventTemplateVersions;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "disabled")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDisabled() {
        return disabled;
    }

    public void setDisabled(Date disabled) {
        this.disabled = disabled;
    }

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id", nullable = false)
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "eventTemplate")
    public Collection<EventTemplateVersion> getEventTemplateVersions() {
        return eventTemplateVersions;
    }

    public void setEventTemplateVersions(Collection<EventTemplateVersion> eventTemplateVersions) {
        this.eventTemplateVersions = eventTemplateVersions;
    }
}
