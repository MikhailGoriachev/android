package org.goriachev.homework.entities;


// Класс Персона
public class Person {
    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // фамилия
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // имя
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // отчество
    private String patronymic;

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    // конструктор по умолчанию
    public Person() {
    }

    // конструктор инициализирующий
    public Person(int id, String surname, String name, String patronymic) {
        this.setId(id);
        this.setSurname(surname);
        this.setName(name);
        this.setPatronymic(patronymic);
    }
}
