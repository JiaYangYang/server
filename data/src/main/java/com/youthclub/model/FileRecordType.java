package com.youthclub.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Created by frank on 14-1-26.
 */
@Entity
@Table(name = "file_record_type", schema = "public", catalog = "web_app")
public class FileRecordType {
    private int id;
    private String name;
    private String entity;
    private String directory;
    private Collection<FileRecord> fileRecordsById;

    @Id
    @Column(name = "id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileRecordType that = (FileRecordType) o;

        if (id != that.id) return false;
        if (directory != null ? !directory.equals(that.directory) : that.directory != null) return false;
        if (entity != null ? !entity.equals(that.entity) : that.entity != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        result = 31 * result + (directory != null ? directory.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "fileRecordTypeByFileRecordTypeId")
    public Collection<FileRecord> getFileRecordsById() {
        return fileRecordsById;
    }

    public void setFileRecordsById(Collection<FileRecord> fileRecordsById) {
        this.fileRecordsById = fileRecordsById;
    }
}
