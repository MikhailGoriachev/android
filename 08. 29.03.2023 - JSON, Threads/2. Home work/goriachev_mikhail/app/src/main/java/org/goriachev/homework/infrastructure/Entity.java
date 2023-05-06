package org.goriachev.homework.infrastructure;


import android.content.Context;
import android.database.Cursor;

// Интерфейс для метода загрузки сущности из базы данных
public abstract class Entity<T> {

    public static final String ID = "_id";

    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // получить имя таблицы
    public abstract String getTableName();

    // метода загрузки сущности из базы данных
    public static <T> T loadEntity(Context context, Cursor cursor) {
        return null;
    }
}
