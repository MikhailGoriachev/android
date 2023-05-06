package org.goriachev.homework.models.webData;


// Класс Пост
public class Post {

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
    public Post() {
    }

    // конструктор инициализирующий
    public Post(int id, String title, String body) {
        this.setId(id);
        this.setTitle(title);
        this.setBody(body);
    }
}
