package org.goriachev.homework.jsonConverters;


import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.goriachev.homework.entities.Appointment;

import java.lang.reflect.Type;


public class AppointmentJsonConverter implements JsonSerializer<Appointment>, JsonDeserializer<Appointment> {

    // контекст приложения
    private final Context appContext;

    public AppointmentJsonConverter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public JsonElement serialize(Appointment src, Type typeOfSrc, JsonSerializationContext context) {
        return src.getJsonFromEntity();
    }

    @Override
    public Appointment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Appointment.getEntityFromJson(appContext, json.getAsJsonObject());
    }
}
