package org.goriachev.homework.jsonConverters;


import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.goriachev.homework.entities.Doctor;

import java.lang.reflect.Type;


public class DoctorJsonConverter implements JsonSerializer<Doctor>, JsonDeserializer<Doctor> {

    // контекст приложения
    private final Context appContext;

    public DoctorJsonConverter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public JsonElement serialize(Doctor src, Type typeOfSrc, JsonSerializationContext context) {
        return src.getJsonFromEntity();
    }

    @Override
    public Doctor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Doctor.getEntityFromJson(appContext, json.getAsJsonObject());
    }
}
