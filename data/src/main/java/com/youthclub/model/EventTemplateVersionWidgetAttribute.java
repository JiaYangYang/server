package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template_version_widget_attribute", schema = "public", catalog = "web_app")
public class EventTemplateVersionWidgetAttribute {
    private int id;
    private String value;
    private EventTemplateVersionWidget eventTemplateVersionWidget;
    private EventTemplateWidgetTypeAttribute eventTemplateWidgetTypeAttribute;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "event_template_version_widget_id", referencedColumnName = "id", nullable = false)
    public EventTemplateVersionWidget getEventTemplateVersionWidget() {
        return eventTemplateVersionWidget;
    }

    public void setEventTemplateVersionWidget(EventTemplateVersionWidget eventTemplateVersionWidget) {
        this.eventTemplateVersionWidget = eventTemplateVersionWidget;
    }

    @ManyToOne
    @JoinColumn(name = "event_template_widget_type_attribute_id", referencedColumnName = "id", nullable = false)
    public EventTemplateWidgetTypeAttribute getEventTemplateWidgetTypeAttribute() {
        return eventTemplateWidgetTypeAttribute;
    }

    public void setEventTemplateWidgetTypeAttribute(EventTemplateWidgetTypeAttribute eventTemplateWidgetTypeAttribute) {
        this.eventTemplateWidgetTypeAttribute = eventTemplateWidgetTypeAttribute;
    }
}
