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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author frank
 */
@Entity
@Table(name = "session", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s"),
        @NamedQuery(name = "Session.currentSession", query = "SELECT e FROM Session e WHERE e.sessionId=:sessionId AND e.ip=:ip and e.expireTime>=:expireTime"),
        @NamedQuery(name = "Session.beforeDate", query = "SELECT e FROM Session e WHERE e.lastRequest<=:date")
})
public class Session extends EntityBase<Session> {
    private static final long serialVersionUID = 1L;

    private int id;
    private String ip;
    private String sessionId;
    private Date startTime;
    private Date expireTime;
    private Date lastRequest;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic(optional = false)
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    @Basic(optional = false)
    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Basic(optional = false)
    @Column(name = "last_request")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
