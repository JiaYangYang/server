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
import javax.persistence.Table;

/**
 * @author frank
 */
@Entity
@Table(name = "participant_field_requirement", schema = "public")
@NamedQueries({
})
public class ParticipantFieldRequirement extends EntityBase<ParticipantFieldRequirement> {


    private int id;
    private boolean participantName;
    private boolean email;
    private boolean contactPhone;
    private boolean age;
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
    @Column(name = "participant_name")
    public boolean getParticipantName() {
        return participantName;
    }

    public void setParticipantName(boolean participantName) {
        this.participantName = participantName;
    }

    @Basic(optional = false)
    @Column(name = "email")
    public boolean getEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    @Basic(optional = false)
    @Column(name = "contact_phone")
    public boolean getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(boolean contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Basic(optional = false)
    @Column(name = "age")
    public boolean getAge() {
        return age;
    }

    public void setAge(boolean age) {
        this.age = age;
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
