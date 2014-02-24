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
@Table(name = "event", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Event.all", query = "SELECT e FROM Event e"),
        @NamedQuery(name = "Event.withCreator", query = "SELECT e FROM Event e WHERE e.created = :created")
})
public class Event extends EntityBase<Event> {

    private int id;
    private Date created;
    private Date disabled;
    private String disabledReason;
    private Date expiry;
    private Date verified;
    private String verifiedReason;
    private User verifiedBy;
    private User disabledBy;
    private User createdBy;
    private EventType eventType;
    private List<EventVersion> eventVersions;

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
            {
                    @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL}),
                    @Permission(roleType = RoleType.USER, accessLevel = {AccessLevel.READ})
            }
    )
    @Column(name = "disabled")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDisabled() {
        return disabled;
    }

    public void setDisabled(Date disabled) {
        this.disabled = disabled;
    }

    @Column(name = "disabled_reason")
    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }

    @Column(name = "expiry")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    @Column(name = "verified")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getVerified() {
        return verified;
    }

    public void setVerified(Date verified) {
        this.verified = verified;
    }

    @Column(name = "verified_reason")
    public String getVerifiedReason() {
        return verifiedReason;
    }

    public void setVerifiedReason(String verifiedReason) {
        this.verifiedReason = verifiedReason;
    }

    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    @ManyToOne
    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    @AttributeAccess(
            @Permission(roleType = RoleType.ADMIN, accessLevel = {AccessLevel.ALL})
    )
    @JoinColumn(name = "disabled_by", referencedColumnName = "id")
    @ManyToOne
    public User getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(User disabledBy) {
        this.disabledBy = disabledBy;
    }

    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @AttributeAccess(
            @Permission(roleType = RoleType.ALL, accessLevel = {AccessLevel.READ})
    )
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    public List<EventVersion> getEventVersions() {
        return eventVersions;
    }

    public void setEventVersions(List<EventVersion> eventVersions) {
        this.eventVersions = eventVersions;
    }
}
