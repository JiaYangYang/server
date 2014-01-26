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
@Table(name = "file_record_type", schema = "public")
public class FileRecordType extends EntityBase<FileRecordType> {
    private int id;
    private String name;
    private String entity;
    private String directory;
    private Collection<FileRecord> fileRecords;

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
    @Column(name = "entity")
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Basic
    @Column(name = "directory")
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @OneToMany(mappedBy = "fileRecordType")
    public Collection<FileRecord> getFileRecords() {
        return fileRecords;
    }

    public void setFileRecords(Collection<FileRecord> fileRecords) {
        this.fileRecords = fileRecords;
    }
}
