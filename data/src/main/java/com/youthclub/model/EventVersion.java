package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "event_version", schema = "public")
@NamedQueries({
        @NamedQuery(name = "EventVersion.withEvent", query = "SELECT e FROM EventVersion e WHERE e.event=:event")})
public class EventVersion extends EntityBase<EventVersion> implements Comparable<EventVersion> {

    private int id;
    private String eventName;
    private String personName;
    private String gender;
    private String mobilePh;
    private String note;
    private Date startTime;
    private Date endTime;
    private Date updated;
    private List<FeeInstance> feeInstances;
    private Event event;
    private List<ParticipantFieldRequirement> participantFieldRequirements;
    private List<EventLocation> eventLocations;

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
    @Column(name = "event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Basic(optional = false)
    @Column(name = "person_name")
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Basic(optional = false)
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic(optional = false)
    @Column(name = "mobile_ph")
    public String getMobilePh() {
        return mobilePh;
    }

    public void setMobilePh(String mobilePh) {
        this.mobilePh = mobilePh;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic(optional = false)
    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventVersion")
    public List<FeeInstance> getFeeInstances() {
        return feeInstances;
    }

    public void setFeeInstances(List<FeeInstance> feeInstances) {
        this.feeInstances = feeInstances;
    }

    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventVersion")
    public List<ParticipantFieldRequirement> getParticipantFieldRequirements() {
        return participantFieldRequirements;
    }

    public void setParticipantFieldRequirements(List<ParticipantFieldRequirement> participantFieldRequirements) {
        this.participantFieldRequirements = participantFieldRequirements;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventVersion")
    public List<EventLocation> getEventLocations() {
        return eventLocations;
    }

    public void setEventLocations(List<EventLocation> eventLocations) {
        this.eventLocations = eventLocations;
    }

    @Override
    public int compareTo(EventVersion that) {
        return getUpdated().compareTo(that.getUpdated());
    }
}
