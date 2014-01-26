package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template_widget_type_attribute", schema = "public")
public class EventTemplateWidgetTypeAttribute extends EntityBase<EventTemplateWidgetTypeAttribute> {
    private int id;
    private String name;
    private String label;
    private String pattern;
    private EventTemplateWidgetType eventTemplateWidgetTypeForTemplate;
    private EventTemplateWidgetType eventTemplateWidgetTypeForWidget;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "pattern")
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @ManyToOne
    @JoinColumn(name = "event_template_widget_type_id", referencedColumnName = "id", nullable = false)
    public EventTemplateWidgetType getEventTemplateWidgetTypeForTemplate() {
        return eventTemplateWidgetTypeForTemplate;
    }

    public void setEventTemplateWidgetTypeForTemplate(EventTemplateWidgetType eventTemplateWidgetTypeForTemplate) {
        this.eventTemplateWidgetTypeForTemplate = eventTemplateWidgetTypeForTemplate;
    }

    @ManyToOne
    @JoinColumn(name = "widget_type_id", referencedColumnName = "id")
    public EventTemplateWidgetType getEventTemplateWidgetTypeForWidget() {
        return eventTemplateWidgetTypeForWidget;
    }

    public void setEventTemplateWidgetTypeForWidget(EventTemplateWidgetType eventTemplateWidgetTypeForWidget) {
        this.eventTemplateWidgetTypeForWidget = eventTemplateWidgetTypeForWidget;
    }
}
