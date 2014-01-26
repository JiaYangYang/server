package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template_version_widget", schema = "public")
public class EventTemplateVersionWidget extends EntityBase<EventTemplateVersionWidget> {
    private int id;
    private Integer priority;
    private String label;
    private EventTemplateVersion eventTemplateVersion;
    private EventTemplateWidgetType eventTemplateWidgetType;
    private Collection<EventTemplateVersionWidgetAttribute> eventTemplateVersionWidgetAttributes;

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
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
    @JoinColumn(name = "event_template_widget_type_id", referencedColumnName = "id")
    public EventTemplateWidgetType getEventTemplateWidgetType() {
        return eventTemplateWidgetType;
    }

    public void setEventTemplateWidgetType(EventTemplateWidgetType eventTemplateWidgetType) {
        this.eventTemplateWidgetType = eventTemplateWidgetType;
    }

    @OneToMany(mappedBy = "eventTemplateVersionWidget")
    public Collection<EventTemplateVersionWidgetAttribute> getEventTemplateVersionWidgetAttributes() {
        return eventTemplateVersionWidgetAttributes;
    }

    public void setEventTemplateVersionWidgetAttributes(Collection<EventTemplateVersionWidgetAttribute> eventTemplateVersionWidgetAttributes) {
        this.eventTemplateVersionWidgetAttributes = eventTemplateVersionWidgetAttributes;
    }
}
