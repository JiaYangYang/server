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
@Table(name = "user_detail", schema = "public")
@NamedQueries({
        @NamedQuery(name = "UserDetail.withUser", query = "SELECT u FROM UserDetail u where u.user=:user")
})
public class UserDetail extends EntityBase<UserDetail> {

    private int id;
    private String identificationNumber;
    private byte[] identificationAttachment;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthday;
    private String homePh;
    private String workPh;
    private String mobilePh;
    private String postCode;
    private String securityQuestion1;
    private String securityAnswer1;
    private String country;
    private String state;
    private String city;
    private Date verified;
    private User verifiedBy;
    private User user;
    private IdentificationType identificationType;

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

    @Column(name = "identification_number")
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Column(name = "identification_attachment")
    public byte[] getIdentificationAttachment() {
        return identificationAttachment;
    }

    public void setIdentificationAttachment(byte[] identificationAttachment) {
        this.identificationAttachment = identificationAttachment;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "home_ph")
    public String getHomePh() {
        return homePh;
    }

    public void setHomePh(String homePh) {
        this.homePh = homePh;
    }

    @Column(name = "work_ph")
    public String getWorkPh() {
        return workPh;
    }

    public void setWorkPh(String workPh) {
        this.workPh = workPh;
    }

    @Column(name = "mobile_ph")
    public String getMobilePh() {
        return mobilePh;
    }

    public void setMobilePh(String mobilePh) {
        this.mobilePh = mobilePh;
    }

    @Column(name = "post_code")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "security_question1")
    public String getSecurityQuestion1() {
        return securityQuestion1;
    }

    public void setSecurityQuestion1(String securityQuestion1) {
        this.securityQuestion1 = securityQuestion1;
    }

    @Column(name = "security_answer1")
    public String getSecurityAnswer1() {
        return securityAnswer1;
    }

    public void setSecurityAnswer1(String securityAnswer1) {
        this.securityAnswer1 = securityAnswer1;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "verified")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getVerified() {
        return verified;
    }

    public void setVerified(Date verified) {
        this.verified = verified;
    }

    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    @ManyToOne
    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "identification_type_id", referencedColumnName = "id")
    @ManyToOne
    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }
}
