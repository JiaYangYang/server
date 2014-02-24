package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author frank
 */
@Entity
@Table(name = "fee_type", schema = "public")
@NamedQueries({
        @NamedQuery(name = "FeeType.all", query = "SELECT f FROM FeeType f ORDER BY f.priority")
})
public class FeeType extends EntityBase<FeeType> {

    private int id;
    private String name;
    private boolean compulsory;
    private Integer priority;
    private List<FeeInstance> feeInstances;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "compulsory")
    @Basic(optional = false)
    public boolean getCompulsory() {
        return compulsory;
    }

    public void setCompulsory(boolean compulsory) {
        this.compulsory = compulsory;
    }

    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feeType")
    public List<FeeInstance> getFeeInstances() {
        return feeInstances;
    }

    public void setFeeInstances(List<FeeInstance> feeInstances) {
        this.feeInstances = feeInstances;
    }
}
