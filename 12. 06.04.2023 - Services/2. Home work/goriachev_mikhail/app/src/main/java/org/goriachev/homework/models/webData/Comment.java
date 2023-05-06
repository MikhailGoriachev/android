package org.goriachev.homework.models.webData;


import java.io.Serializable;

// Комментарий
public class Comment implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // наименование
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // адрес электронной почты
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // текст
    public String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    // конструктор по умолчанию
    public Comment() {
    }

    // конструктор инициализирующий
    public Comment(int id, String name, String email, String body) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setBody(body);
    }
}
