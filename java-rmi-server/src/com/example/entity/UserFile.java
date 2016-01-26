package com.example.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * File entity
 */
public class UserFile implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Date uploadDate;

    private User owner;

    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getSize() { return size; }

    public void setSize(Integer size) { this.size = size; }
}
