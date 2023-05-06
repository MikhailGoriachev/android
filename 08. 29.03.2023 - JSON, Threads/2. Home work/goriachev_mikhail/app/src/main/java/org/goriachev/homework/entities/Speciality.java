package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.JsonObject;

import org.goriachev.homework.infrastructure.JsonEntity;

// Класс Специальность
public class Speciality extends JsonEntity<Speciality> {

    // название таблицы
    public static final String TABLE_NAME = "specialities";

    // поля
    public static final String SPECIALITY_NAME = "name";

    // название
    private String specialityName;

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }


    // конструктор по умолчанию
    public Speciality() {
    }

    // конструктор инициализирующий
    public Speciality(int id, String specialityName) {
        this.setId(id);
        this.setSpecialityName(specialityName);
    }


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Speciality loadEntity(Context context, Cursor cursor) {
        return new Speciality(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(SPECIALITY_NAME))
        );
    }

    // установка данных из JSON
    public static Speciality getEntityFromJson(Context context, JsonObject jsonObject) {
        return new Speciality(
                jsonObject.get(ID).getAsInt(),
                jsonObject.get(SPECIALITY_NAME).getAsString()
        );
    }

    // получение данных в формате JSON
    @Override
    public JsonObject getJsonFromEntity() {
        var object = new JsonObject();

        object.addProperty(Speciality.ID, getId());
        object.addProperty(Speciality.SPECIALITY_NAME, specialityName);

        return object;
    }
}
