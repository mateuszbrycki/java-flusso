package com.example.entity;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User entity
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String mail;

    private String password;

    private Date registrationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
