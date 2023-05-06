package org.goriachev.homework.jsonConverters;


import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.goriachev.homework.entities.Speciality;

import java.lang.reflect.Type;


public class SpecialityJsonConverter implements JsonSerializer<Speciality>, JsonDeserializer<Speciality> {

    // контекст приложения
    private final Context appContext;

    public SpecialityJsonConverter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public JsonElement serialize(Speciality src, Type typeOfSrc, JsonSerializationContext context) {
        return src.getJsonFromEntity();
    }

    @Override
    public Speciality deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Speciality.getEntityFromJson(appContext, json.getAsJsonObject());
    }
}
