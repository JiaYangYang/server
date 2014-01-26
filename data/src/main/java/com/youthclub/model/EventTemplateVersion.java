package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template_version", schema = "public")
public class EventTemplateVersion {
    private int id;
    private Date created;
    private Date disabled;
    private Collection<EventInstance> eventInstances;
    private EventTemplate eventTemplate;
    private User user;
    private Collection<EventTemplateVersionWidget> eventTemplateVersionWidgets;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "disabled")
    public Date getDisabled() {
        return disabled;
    }

    public void setDisabled(Date disabled) {
        this.disabled = disabled;
    }

    @OneToMany(mappedBy = "eventTemplateVersion")
    public Collection<EventInstance> getEventInstances() {
        return eventInstances;
    }

    public void setEventInstances(Collection<EventInstance> eventInstances) {
        this.eventInstances = eventInstances;
    }

    @ManyToOne
    @JoinColumn(name = "event_template_id", referencedColumnName = "id", nullable = false)
    public EventTemplate getEventTemplate() {
        return eventTemplate;
    }

    public void setEventTemplate(EventTemplate eventTemplate) {
        this.eventTemplate = eventTemplate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return user;
    }

    public void setUserByUserId(User userByUserId) {
        this.user = userByUserId;
    }

    @OneToMany(mappedBy = "eventTemplateVersion")
    public Collection<EventTemplateVersionWidget> getEventTemplateVersionWidgets() {
        return eventTemplateVersionWidgets;
    }

    public void setEventTemplateVersionWidgets(Collection<EventTemplateVersionWidget> eventTemplateVersionWidgets) {
        this.eventTemplateVersionWidgets = eventTemplateVersionWidgets;
    }
}
