package org.goriachev.homework.models.webData;

// Класс Альбом
public class Album {

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

    // текст
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
