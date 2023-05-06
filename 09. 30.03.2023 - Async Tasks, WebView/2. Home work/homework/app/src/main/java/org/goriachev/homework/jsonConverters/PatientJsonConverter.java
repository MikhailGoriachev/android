package org.goriachev.homework.jsonConverters;


import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.goriachev.homework.entities.Patient;

import java.lang.reflect.Type;


public class PatientJsonConverter implements JsonSerializer<Patient>, JsonDeserializer<Patient> {

    // контекст приложения
    private final Context appContext;

    public PatientJsonConverter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public JsonElement serialize(Patient src, Type typeOfSrc, JsonSerializationContext context) {
        return src.getJsonFromEntity();
    }

    @Override
    public Patient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Patient.getEntityFromJson(appContext, json.getAsJsonObject());
    }
}
