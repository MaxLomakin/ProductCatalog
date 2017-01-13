package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
@Entity
@Table(name="objects")
public class DataObject {

    @Id
    @GeneratedValue
    @Column(name="object_id")
    private Integer objectId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="object_type_id")
    private ObjectType objectType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="picture_id")
    private Picture picture;

    public DataObject() {
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.objectId == null);
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Object [id=" + objectId + ", name=" + name + ", objectType=" + objectType.getName()
                + ", description=" + description + "]" + isNew();
    }
}
