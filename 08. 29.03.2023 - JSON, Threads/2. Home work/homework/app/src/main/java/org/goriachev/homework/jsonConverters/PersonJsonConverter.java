package org.goriachev.homework.jsonConverters;


import android.content.Context;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.goriachev.homework.entities.Person;

import java.lang.reflect.Type;


public class PersonJsonConverter implements JsonSerializer<Person>, JsonDeserializer<Person> {

    // контекст приложения
    private final Context appContext;

    public PersonJsonConverter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public JsonElement serialize(Person src, Type typeOfSrc, JsonSerializationContext context) {
        return src.getJsonFromEntity();
    }

    @Override
    public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Person.getEntityFromJson(appContext, json.getAsJsonObject());
    }
}
