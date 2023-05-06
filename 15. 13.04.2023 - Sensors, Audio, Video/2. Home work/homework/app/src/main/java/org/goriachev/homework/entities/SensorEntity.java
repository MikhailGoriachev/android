package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.infrastructure.Entity;

// Класс Датчик
public class SensorEntity extends Entity<SensorEntity> {

    // название таблицы
    public static final String TABLE_NAME = "sensors";

    // поля
    public static final String IDENTIFIER_SENSOR = "identifier_sensor";
    public static final String NAME = "name";

    // идентификатор сенсора (не id записи)
    private int identifierSensor;

    public int getIdentifierSensor() {
        return identifierSensor;
    }

    public void setIdentifierSensor(int identifierSensor) {
        this.identifierSensor = identifierSensor;
    }

    // название сенсора
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // конструктор по умолчанию
    public SensorEntity() {
    }

    // конструктор инициализирующий
    public SensorEntity(int id, int identifierSensor, String name) {
        this.setId(id);
        this.setIdentifierSensor(identifierSensor);
        this.setName(name);
    }

    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static SensorEntity loadEntity(Context context, Cursor cursor) {
        return new SensorEntity(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getInt(cursor.getColumnIndex(IDENTIFIER_SENSOR)),
                cursor.getString(cursor.getColumnIndex(NAME))
        );
    }
}
