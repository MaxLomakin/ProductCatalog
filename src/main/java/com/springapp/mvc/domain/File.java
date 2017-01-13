package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * Created by Max on 11.01.2017.
 */
@Entity
@Table(name="files")
public class File {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="file_id")
    private int fileId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "object_id")
    private Integer objectId;

    public File() {
    }

    public File(String name, String url, String type, String description, Integer objectId) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.description = description;
        this.objectId = objectId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }
}
