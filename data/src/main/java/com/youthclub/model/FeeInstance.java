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
import java.math.BigDecimal;

/**
 * @author frank
 */
@Entity
@Table(name = "fee_instance", schema = "public")
@NamedQueries({
        @NamedQuery(name = "FeeInstance.withEventVersion", query = "SELECT f FROM FeeInstance f WHERE f.eventVersion=:eventVersion")
})
public class FeeInstance extends EntityBase<FeeInstance> {

    private int id;
    private boolean compulsory;
    private Boolean prePaid;
    private BigDecimal amount;
    private String note;
    private FeeType feeType;
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
    @Column(name = "compulsory")
    public boolean getCompulsory() {
        return compulsory;
    }

    public void setCompulsory(boolean compulsory) {
        this.compulsory = compulsory;
    }

    @Column(name = "pre_paid")
    public Boolean getPrePaid() {
        return prePaid;
    }

    public void setPrePaid(Boolean prePaid) {
        this.prePaid = prePaid;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JoinColumn(name = "fee_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
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
