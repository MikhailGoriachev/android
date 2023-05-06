package org.goriachev.homework.models;

// Класс Альбом
public class Album {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Album() {
    }

    public Album(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
