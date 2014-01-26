package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "event_template_widget_type", schema = "public")
public class EventTemplateWidgetType extends EntityBase<EventTemplateWidgetType> {
    private int id;
    private String name;
    private String label;
    private Boolean imageUpload;
    private Boolean imageShow;
    private Boolean fileUpload;
    private Boolean fileDownload;
    private Collection<EventTemplateVersionWidget> eventTemplateVersionWidgets;
    private Collection<EventTemplateWidgetTypeAttribute> eventTemplateWidgetTypeAttributesForTemplate;
    private Collection<EventTemplateWidgetTypeAttribute> eventTemplateWidgetTypeAttributesForWidget;

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
    @Column(name = "image_upload")
    public Boolean getImageUpload() {
        return imageUpload;
    }

    public void setImageUpload(Boolean imageUpload) {
        this.imageUpload = imageUpload;
    }

    @Basic
    @Column(name = "image_show")
    public Boolean getImageShow() {
        return imageShow;
    }

    public void setImageShow(Boolean imageShow) {
        this.imageShow = imageShow;
    }

    @Basic
    @Column(name = "file_upload")
    public Boolean getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Boolean fileUpload) {
        this.fileUpload = fileUpload;
    }

    @Basic
    @Column(name = "file_download")
    public Boolean getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(Boolean fileDownload) {
        this.fileDownload = fileDownload;
    }

    @OneToMany(mappedBy = "eventTemplateWidgetType")
    public Collection<EventTemplateVersionWidget> getEventTemplateVersionWidgets() {
        return eventTemplateVersionWidgets;
    }

    public void setEventTemplateVersionWidgets(Collection<EventTemplateVersionWidget> eventTemplateVersionWidgets) {
        this.eventTemplateVersionWidgets = eventTemplateVersionWidgets;
    }

    @OneToMany(mappedBy = "eventTemplateWidgetTypeForTemplate")
    public Collection<EventTemplateWidgetTypeAttribute> getEventTemplateWidgetTypeAttributesForTemplate() {
        return eventTemplateWidgetTypeAttributesForTemplate;
    }

    public void setEventTemplateWidgetTypeAttributesForTemplate(Collection<EventTemplateWidgetTypeAttribute> eventTemplateWidgetTypeAttributesForTemplate) {
        this.eventTemplateWidgetTypeAttributesForTemplate = eventTemplateWidgetTypeAttributesForTemplate;
    }

    @OneToMany(mappedBy = "eventTemplateWidgetTypeForWidget")
    public Collection<EventTemplateWidgetTypeAttribute> getEventTemplateWidgetTypeAttributesForWidget() {
        return eventTemplateWidgetTypeAttributesForWidget;
    }

    public void setEventTemplateWidgetTypeAttributesForWidget(Collection<EventTemplateWidgetTypeAttribute> eventTemplateWidgetTypeAttributesForWidget) {
        this.eventTemplateWidgetTypeAttributesForWidget = eventTemplateWidgetTypeAttributesForWidget;
    }
}
