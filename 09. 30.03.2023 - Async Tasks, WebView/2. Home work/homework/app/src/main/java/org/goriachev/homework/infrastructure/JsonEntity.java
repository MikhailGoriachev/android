package org.goriachev.homework.infrastructure;


import android.content.Context;

import com.google.gson.JsonObject;

// Интерфейс для метода загрузки сущности из JSON
public abstract class JsonEntity<T> extends Entity<T> {

    // установка данных из JSON
    public static <T> T getEntityFromJson(Context context, JsonObject jsonObject) {
        return null;
    }

    // получение данных в формате JSON
    public abstract JsonObject getJsonFromEntity();
}
