package com.youthclub.model;

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

/**
 * @author frank
 */
@Entity
@Table(name = "event_location", schema = "public")
@NamedQueries({
        @NamedQuery(name = "EventLocation.all", query = "SELECT e FROM EventLocation e")
})
public class EventLocation extends EntityBase<EventLocation> {

    private int id;
    private String location;
    private Integer priority;
    private EventVersion eventVersion;

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
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JoinColumn(name = "event_version_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public EventVersion getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(EventVersion eventVersion) {
        this.eventVersion = eventVersion;
    }
}
