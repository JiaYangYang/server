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
@Table(name = "file_record", schema = "public")
public class FileRecord extends EntityBase<FileRecord> {
    private int id;
    private Integer entityId;
    private String filename;
    private String mimeType;
    private String subDir;
    private FileRecordType fileRecordType;

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

    @ManyToOne
    @JoinColumn(name = "file_record_type_id", referencedColumnName = "id", nullable = false)
    public FileRecordType getFileRecordType() {
        return fileRecordType;
    }

    public void setFileRecordType(FileRecordType fileRecordType) {
        this.fileRecordType = fileRecordType;
    }
}
