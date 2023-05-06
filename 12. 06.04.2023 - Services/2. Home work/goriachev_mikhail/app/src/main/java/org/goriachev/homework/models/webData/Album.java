package org.goriachev.homework.models.webData;

import java.io.Serializable;

// Альбом
public class Album implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // заголовок
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    // конструктор по умолчанию
    public Album() {
    }

    // конструктор инициализирующий
    public Album(int id, String title) {
        this.setId(id);
        this.setTitle(title);
    }
}
