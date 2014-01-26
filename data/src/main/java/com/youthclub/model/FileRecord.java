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
@Table(name = "file_record", schema = "public", catalog = "web_app")
public class FileRecord {
    private int id;
    private Integer entityId;
    private String filename;
    private String mimeType;
    private String subDir;
    private FileRecordType fileRecordTypeByFileRecordTypeId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entity_id")
    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "mime_type")
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Basic
    @Column(name = "sub_dir")
    public String getSubDir() {
        return subDir;
    }

    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileRecord that = (FileRecord) o;

        if (id != that.id) return false;
        if (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (mimeType != null ? !mimeType.equals(that.mimeType) : that.mimeType != null) return false;
        if (subDir != null ? !subDir.equals(that.subDir) : that.subDir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (subDir != null ? subDir.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "file_record_type_id", referencedColumnName = "id", nullable = false)
    public FileRecordType getFileRecordTypeByFileRecordTypeId() {
        return fileRecordTypeByFileRecordTypeId;
    }

    public void setFileRecordTypeByFileRecordTypeId(FileRecordType fileRecordTypeByFileRecordTypeId) {
        this.fileRecordTypeByFileRecordTypeId = fileRecordTypeByFileRecordTypeId;
    }
}
