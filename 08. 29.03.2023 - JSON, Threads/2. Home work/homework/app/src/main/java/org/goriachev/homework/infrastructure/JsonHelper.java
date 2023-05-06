package org.goriachev.homework.infrastructure;


import android.content.Context;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonHelper {

    // имя файла
    private static final String FILE_NAME = "polyclinic.json";

    // экспорт данных
    public static <T> boolean exportToJson(Context context, T data, Type dataType, JsonSerializer<?> converter) {
        boolean result = false;

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(dataType, converter);
        var gson = builder.create();

        var jsonString = gson.toJson(data);

        try (FileOutputStream stream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            stream.write(jsonString.getBytes());
            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    // импорт данных
    public static <T> List<T> importListFromJson(Context context, Type itemDataType, Type arrayDataType, JsonDeserializer<T> converter) {
        try (InputStreamReader stream = new InputStreamReader(context.openFileInput(FILE_NAME))){

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(itemDataType, converter);

            var gson = builder.create();

            return new ArrayList<>(Arrays.asList(gson.fromJson(stream, arrayDataType)));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
