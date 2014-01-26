/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "user_log", schema = "public")
@NamedQueries({
        @NamedQuery(name = "UserLog.findAll", query = "SELECT u FROM UserLog u"),
        @NamedQuery(name = "UserLog.withUserAndIp", query = "SELECT e FROM UserLog e WHERE e.user=:user AND e.ip=:ip AND e.logoutTime IS NULL ORDER BY e.loginTime DESC"),
        @NamedQuery(name = "UserLog.withIp", query = "SELECT e FROM UserLog e WHERE e.ip=:ip AND e.user.bindIp IS NOT NULL ORDER BY e.loginTime DESC")
})
public class UserLog extends EntityBase<UserLog> {
    private int id;
    private String ip;
    private String userAgent;
    private Date loginTime;
    private Date logoutTime;
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

    @Column(name = "user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time")
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logout_time")
    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
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
