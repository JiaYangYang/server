package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_instance", schema = "public")
public class EventInstance extends EntityBase<EventInstance> {
    private int id;
    private String name;
    private Date created;
    private Date disabled;
    private EventTemplateVersion eventTemplateVersion;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @ManyToOne
    @JoinColumn(name = "event_template_version_id", referencedColumnName = "id", nullable = false)
    public EventTemplateVersion getEventTemplateVersion() {
        return eventTemplateVersion;
    }

    public void setEventTemplateVersion(EventTemplateVersion eventTemplateVersion) {
        this.eventTemplateVersion = eventTemplateVersion;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
