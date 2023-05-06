package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.JsonObject;

import org.goriachev.homework.infrastructure.JsonEntity;


// Класс Персона
public class Person extends JsonEntity<Person> {

    // название таблицы
    public static final String TABLE_NAME = "persons";

    // поля
    public static final String SURNAME = "surname";
    public static final String NAME = "name";
    public static final String PATRONYMIC = "patronymic";


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


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Person loadEntity(Context context, Cursor cursor) {
        return new Person(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(SURNAME)),
                cursor.getString(cursor.getColumnIndex(NAME)),
                cursor.getString(cursor.getColumnIndex(PATRONYMIC))
        );
    }

    // установка данных из JSON
    public static Person getEntityFromJson(Context context, JsonObject jsonObject) {

        return new Person(
                jsonObject.get(ID).getAsInt(),
                jsonObject.get(SURNAME).getAsString(),
                jsonObject.get(NAME).getAsString(),
                jsonObject.get(PATRONYMIC).getAsString()
        );
    }

    // получение данных в формате JSON
    @Override
    public JsonObject getJsonFromEntity() {
        var object = new JsonObject();

        object.addProperty(Person.ID, getId());
        object.addProperty(Person.SURNAME, surname);
        object.addProperty(Person.NAME, name);
        object.addProperty(Person.PATRONYMIC, patronymic);

        return object;
    }
}
