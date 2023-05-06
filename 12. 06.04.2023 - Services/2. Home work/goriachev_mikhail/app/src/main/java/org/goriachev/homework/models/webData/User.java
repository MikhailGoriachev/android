package org.goriachev.homework.models.webData;

import java.io.Serializable;

// Пользователь
public class User implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // имя
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ник
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // почта
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // сайт
    public String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public User() {
    }

    public User(int id, String name, String username, String email, String website) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.website = website;
    }
}
